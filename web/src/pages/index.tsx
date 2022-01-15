import { useRouter } from "next/router";
import React, { useContext } from "react";
import { Socket, SocketContext } from "../context/context";

function LinkPage() {
	const router = useRouter();
	const { setSocket, setMessage } = useContext(SocketContext);

	const initSocket = () => {
		const socket = new WebSocket("ws://localhost:12200");
		socket.onopen = () => {
			router.push("auth");
			console.log("open connection");
		};
		setSocket(new Socket(socket, setMessage));
	};

	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center justify-center h-full bg-gray-500'>
				<div className='ml-6 mr-2 text-5xl btn btn-accent' onClick={initSocket}>
					Link Start
				</div>
			</div>
		</div>
	);
}

export default LinkPage;
