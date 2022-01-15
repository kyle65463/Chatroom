import React from "react";
import { Message } from "../models/message";

export class Socket {
	constructor(webSocket: WebSocket, setMessage: (messgae: Message) => void) {
		this.webSocket = webSocket;
		this.setMessage = setMessage;
		this.webSocket.onmessage = (rawMessage) => {
			// Process message
			console.log(rawMessage.data);
			const message: Message = Message.parse(rawMessage);
			this.setMessage(message);
		};
	}

	private webSocket: WebSocket | undefined;
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
		this.webSocket?.send(header + body);
	};
}

interface SocketContext {
	socket?: Socket;
	setSocket: (socket: Socket) => void;
	authToken?: string;
	setAuthToken: (token: string) => void;
	user?: string;
	setUser: (user: string) => void;
	message?: Message;
	setMessage: (message: Message) => void
}

export const SocketContext = React.createContext<SocketContext>({
	socket: undefined,
	setSocket: (socket: Socket) => {},
	authToken: undefined,
	setAuthToken: (token: string) => {},
	user: undefined,
	setUser: (user: string) => {},
	setMessage: (message: Message) => {},
});

export const UseSocketContext = () => React.useContext(SocketContext);
