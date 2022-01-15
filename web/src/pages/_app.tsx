import type { AppProps } from "next/app";
import React, { useState } from "react";
import LinkPage from ".";
import { Socket, SocketContext } from "../context/context";
import { Message } from "../models/message";
import "../styles/globals.css";
import AuthPage from "./auth";
import LoginPage from "./login";
import RegisterPage from "./register";

function MyApp({ Component, pageProps }: AppProps) {
	const [message, setMessage] = useState<Message | undefined>(undefined);
	const [socket, setSocket] = useState<Socket | undefined>(undefined);
	const [authToken, setAuthToken] = useState<string | undefined>(undefined);
	const [user, setUser] = useState<string | undefined>(undefined);

	if (!socket || !socket.webSocket) {
		return (
			<div>
				<SocketContext.Provider
					value={{ socket, setSocket, authToken, setAuthToken, user, setUser, message, setMessage }}
				>
					<LinkPage {...pageProps} />
				</SocketContext.Provider>
			</div>
		);
	}

	if (!authToken && Component! instanceof LoginPage && Component! instanceof RegisterPage) {
		return (
			<div>
				<SocketContext.Provider
					value={{ socket, setSocket, authToken, setAuthToken, user, setUser, message, setMessage }}
				>
					<AuthPage {...pageProps} />
				</SocketContext.Provider>
			</div>
		);
	}

	return (
		<div>
			<SocketContext.Provider
				value={{ socket, setSocket, authToken, setAuthToken, user, setUser, message, setMessage }}
			>
				<Component {...pageProps} />
			</SocketContext.Provider>
		</div>
	);
}

export default MyApp;
