import { useRouter } from "next/router";
import React, { useContext, useState } from "react";
import { Socket, SocketContext } from "../context/context";

function LinkPage() {
	const router = useRouter();
	const { setSocket, setMessage } = useContext(SocketContext);
	const [port, setPort] = useState(12200);

	const initSocket = () => {
		const socket = new WebSocket(`ws://localhost:${port}`);
		socket.onopen = () => {
			router.push("auth");
			console.log("open connection");
		};
		setSocket(new Socket(socket, setMessage));
	};

	const onPortChange = (e: any) => {
		setPort(e.target.value);
	};

	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center justify-center h-full bg-gray-500'>
				<div className='mt-12 ml-6 mr-2 text-5xl btn btn-accent' onClick={initSocket}>
					Link Start
				</div>
				<div className='mt-5'>
					<label htmlFor='port' className='text-white label'>
						Client port:
					</label>
					<input
						id='port'
						className='input input-bordered'
						type='number'
						onChange={onPortChange}
						defaultValue={12200}
					/>
				</div>
			</div>
		</div>
	);
}

export default LinkPage;
