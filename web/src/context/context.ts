import React from "react";
import { Message } from "../models/message";
import { ChatRoom, User } from "../models/user";

export class Socket {
	constructor(webSocket: WebSocket, setMessage: (message: Message) => void) {
		this.webSocket = webSocket;
		this.setMessage = setMessage;
		this.webSocket.onmessage = (rawMessage) => {
			const message: Message = Message.parse(rawMessage);
			this.setMessage(message);
		};
		this.webSocket.onerror = (rawMessage) => {
			console.log("error");
			console.log(rawMessage);
		};
	}

	public webSocket: WebSocket | undefined;
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
		let offset = 0;
		while (offset < body.length) {
			this.webSocket?.send(body.substring(offset, Math.min(offset + 10000, body.length)));
			offset += 10000;
		}
	};
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
