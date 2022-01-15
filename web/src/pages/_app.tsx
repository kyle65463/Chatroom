import type { AppProps } from "next/app";
import React, { useState } from "react";
import { Socket, SocketContext } from "../context/context";
import { Message } from "../models/message";
import "../styles/globals.css";

function MyApp({ Component, pageProps }: AppProps) {
	const [message, setMessage] = useState<Message | undefined>(undefined);
	const [socket, setSocket] = useState<Socket | undefined>(undefined);
	const [authToken, setAuthToken] = useState<string | undefined>(undefined);
	const [user, setUser] = useState<string | undefined>(undefined);

	return (
		<div className='bg-base-200'>
			<SocketContext.Provider value={{ socket, setSocket, authToken, setAuthToken, user, setUser, setMessage }}>
				<Component {...pageProps} />
			</SocketContext.Provider>
		</div>
	);
}

export default MyApp;
