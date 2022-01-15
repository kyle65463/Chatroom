import React from "react";

interface SocketContext {
	socket?: WebSocket;
	setSocket: (socket: WebSocket) => void;
}

export const SocketContext = React.createContext<SocketContext>({
	socket: undefined,
	setSocket: (socket: WebSocket) => {},
});

export const UseSocketContext = () => React.useContext(SocketContext);
