import Link from "next/link";
import Router, { useRouter } from "next/router";
import React, { useContext, useState } from "react";
import { Socket, SocketContext } from "../context/context";
import { UseSocketContext } from "../context/context";

function Home() {
	const router = useRouter();
	const {  setSocket } = useContext(SocketContext);

	const initSocket = () => {
		const newsocket = new WebSocket("ws://localhost:12200");
		newsocket.onopen = () => {
			console.log("open connection");
			let body: string = JSON.stringify({ username: "luca", password: "luca0514" });
			let header: string =
				"POST" +
				" " +
				"/login" +
				" HTTP/1.1\r\n" +
				"Host:localhost\r\n" +
				"Content-Length: " +
				body.length +
				"\r\n" +
				"\r\n";
			newsocket.send(header + body);
		};
		setSocket(new Socket(newsocket));
		router.push("/connect");
	};

	return (
		<div className='h-screen'>
			<div className='flex flex-col justify-center items-center h-full bg-gray-500'>
				<div className='btn ml-6 mr-2 btn-accent text-5xl' onClick={initSocket}>
					Connect
				</div>
			</div>
		</div>
	);
}

export default Home;
