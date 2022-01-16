import React from "react";
import { Header, Message } from "../models/message";
import { ChatRoom, User } from "../models/user";

export class Socket {
	constructor(webSocket: WebSocket, setMessage: (message: Message) => void) {
		this.webSocket = webSocket;
		this.setMessage = setMessage;
		this.webSocket.onmessage = (rawMessage) => {
			const data = rawMessage.data as string;
			if (this.containsHeader(data)) {
				const { headers, rawBody, status } = Message.parseHeader(data);
				this.contentLength = parseInt(headers.find((e) => e.key === "Content-Length")?.value ?? "");
				this.contentLength -= rawBody.length;
				this.headers = headers;
				this.status = status;
				this.body = rawBody;
			} else {
				// All data is rawBody
				if (this.contentLength) {
					this.contentLength -= data.length;
					this.body += data;
				}
			}

			if (this.contentLength != undefined && this.contentLength <= 0) {
				// All body is received
				this.contentLength = undefined;
				const message: Message = Message.parse(this.status, this.headers, this.body);
				this.setMessage(message);
			}
		};
		this.webSocket.onerror = (rawMessage) => {
			console.log("error");
			console.log(rawMessage);
		};
	}

	public webSocket: WebSocket | undefined;
	private status: number = 0;
	private headers: Header[] = [];
	private body: string = "";
	private contentLength: number | undefined;
	private setMessage: (messgae: Message) => void = () => {};

	send = (path: string, body: string, requestType: string, authToken: string) => {
		let header: string =
			requestType +
			" " +
			path +
			" HTTP/1.1\r\n" +
			"Host:localhost\r\n" +
			"Content-Length: " +
			body.length +
			"\r\n" +
			(authToken.length > 0 ? "Authorization: " + authToken + "\r\n" : "") +
			"\r\n";
		this.webSocket?.send(header);
		const chunkSize = 10000;
		let offset = 0;
		while (offset < body.length) {
			this.webSocket?.send(body.substring(offset, Math.min(offset + chunkSize, body.length)));
			offset += chunkSize;
		}
	};

	containsHeader = (data: string) => {
		const lines = data.split("\r\n");
		return lines.length > 2;
	};

	isEnd = (data: string) => {};
}

interface SocketContext {
	socket?: Socket;
	setSocket: (socket: Socket) => void;
	authToken?: string;
	setAuthToken: (token: string) => void;
	user?: User;
	setUser: (user: User) => void;
	message?: Message;
	setMessage: (message: Message) => void;
	chatroom?: ChatRoom;
	setChatRoom: (chatroom?: ChatRoom) => void;
}

export const SocketContext = React.createContext<SocketContext>({
	socket: undefined,
	setSocket: (socket: Socket) => {},
	authToken: undefined,
	setAuthToken: (token: string) => {},
	user: undefined,
	setUser: (user: User) => {},
	setMessage: (message: Message) => {},
	chatroom: undefined,
	setChatRoom: (chatroom?: ChatRoom) => {},
});

export const UseSocketContext = () => React.useContext(SocketContext);
