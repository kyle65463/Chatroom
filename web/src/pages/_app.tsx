import type { AppProps } from "next/app";
import React, { useState } from "react";
import LinkPage from ".";
import { Socket, SocketContext } from "../context/context";
import { Message } from "../models/message";
import { ChatRoom, User } from "../models/user";
import "../styles/globals.css";
import AuthPage from "./auth";
import LoginPage from "./login";
import RegisterPage from "./register";

function MyApp({ Component, pageProps }: AppProps) {
	const [message, setMessage] = useState<Message | undefined>(undefined);
	const [socket, setSocket] = useState<Socket | undefined>(undefined);
	const [authToken, setAuthToken] = useState<string | undefined>(undefined);
	const [user, setUser] = useState<User | undefined>(undefined);
	const [chatroom, setChatRoom] = useState<ChatRoom | undefined>(undefined);

	if (!socket || !socket.webSocket) {
		return (
			<div>
				<SocketContext.Provider
					value={{
						socket,
						setSocket,
						authToken,
						setAuthToken,
						user,
						setUser,
						message,
						setMessage,
						chatroom,
						setChatRoom,
					}}
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
					value={{
						socket,
						setSocket,
						authToken,
						setAuthToken,
						user,
						setUser,
						message,
						setMessage,
						chatroom,
						setChatRoom,
					}}
				>
					<AuthPage {...pageProps} />
				</SocketContext.Provider>
			</div>
		);
	}

	return (
		<div>
			<SocketContext.Provider
				value={{
					socket,
					setSocket,
					authToken,
					setAuthToken,
					user,
					setUser,
					message,
					setMessage,
					chatroom,
					setChatRoom,
				}}
			>
				<Component {...pageProps} />
			</SocketContext.Provider>
		</div>
	);
}

export default MyApp;
