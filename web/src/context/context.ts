import React from "react";
import { Message } from "../models/message";

export class Socket {
	constructor(webSocket: WebSocket) {
		this.webSocket = webSocket;
		this.webSocket.onmessage = (rawMessage) => {
			// Process message
			const message: Message = Message.parse(rawMessage)
			this.message = message;
		};
	}

	public webSocket: WebSocket | undefined;

	send = (payload: string) => {
		this.webSocket?.send(payload);
	};

	public message: Message | undefined;
}

interface SocketContext {
	socket?: Socket;
	setSocket: (socket: Socket) => void;
	authToken?: string;
	setAuthToken: (token: string) => void;
}

export const SocketContext = React.createContext<SocketContext>({
	socket: undefined,
	setSocket: (socket: Socket) => {},
	authToken: undefined,
	setAuthToken: (token: string) => {},
});

export const UseSocketContext = () => React.useContext(SocketContext);
