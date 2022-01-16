import Link from "next/link";
import { useRouter } from "next/router";
import React, { useContext, useEffect } from "react";
import { SocketContext } from "../context/context";
import { Message, NullMessage } from "../models/message";

function Home() {
	const { socket, message, setMessage, user } = useContext(SocketContext);
	const router = useRouter();
	useEffect(() => {}, [message]);

	const LOGOUT = () => {
		socket?.webSocket?.close();
		let nullMessage: Message = new NullMessage([], "");
		setMessage(nullMessage);
		router.push("/");
	};

	return (
		<div className='min-h-screen bg-base-200'>
			<div className='flex flex-col items-center justify-center h-full min-h-screen'>
				<p className='text-4xl font-bold'>
					<span className='text-accent'>{user?.username}</span>'s personal Space
				</p>
				<div className='flex flex-col justify-center m-20'>
					<Link href='/friend'>
						<div className='ml-6 mr-2 btn btn-accent btn-wide'>FRIEND</div>
					</Link>
					<Link href='/chatmenu'>
						<div className='m-10 ml-6 mr-2 btn btn-accent btn-wide'>CHAT MENU</div>
					</Link>
					<div className='ml-6 mr-2 btn btn-wide' onClick={LOGOUT}>
						LOGOUT
					</div>
				</div>
			</div>
		</div>
	);
}

export default Home;
