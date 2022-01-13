import type { AppProps } from "next/app";
import React from "react";
import "../styles/globals.css";

function MyApp({ Component, pageProps }: AppProps) {
	return (
		<div className='bg-base-200'>
			<Component {...pageProps} />
		</div>
	);
}

export default MyApp;
