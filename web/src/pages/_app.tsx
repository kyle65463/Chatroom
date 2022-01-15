import type { AppProps } from "next/app";
import React, {useState} from "react";
import { SocketContext } from "../context/context";
import "../styles/globals.css";

function MyApp({ Component, pageProps }: AppProps) {

	const [socket, setSocket] = useState<WebSocket | undefined>(undefined);

	return (
		<div className='bg-base-200'>
			<SocketContext.Provider value={{ socket, setSocket }}>
				<Component {...pageProps} />
			</SocketContext.Provider>
		</div>
	);
}

export default MyApp;
