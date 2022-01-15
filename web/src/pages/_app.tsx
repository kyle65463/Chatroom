import type { AppProps } from "next/app";
import React, { useState } from "react";
import { Socket, SocketContext } from "../context/context";
import "../styles/globals.css";

function MyApp({ Component, pageProps }: AppProps) {
	const [socket, setSocket] = useState<Socket | undefined>(undefined);
	const [authToken, setAuthToken] = useState<string | undefined>(undefined);

	return (
		<div className='bg-base-200'>
			<SocketContext.Provider value={{ socket, setSocket, authToken, setAuthToken }}>
				<Component {...pageProps} />
			</SocketContext.Provider>
		</div>
	);
}

export default MyApp;
