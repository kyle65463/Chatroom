import Router, { useRouter } from "next/router";
import React, { useContext, useState } from "react";
import { Socket, SocketContext } from "../context/context";

function Home() {
	const router = useRouter();
	const { setSocket, setMessage } = useContext(SocketContext);

	const initSocket = () => {
		const socket = new WebSocket("ws://localhost:12200");
		socket.onopen = () => {
			console.log("open connection");
		};
		setSocket(new Socket(socket, setMessage));
		router.push("/connect");
	};

	return (
		<div className='h-screen'>
			<div className='flex flex-col justify-center items-center h-full bg-gray-500'>
				<div className='btn ml-6 mr-2 btn-accent text-5xl' onClick={initSocket}>
					Link Start
				</div>
			</div>
		</div>
	);
}

export default Home;
