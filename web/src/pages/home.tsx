import Link from "next/link";
import React, { useContext, useEffect, useReducer } from "react";
import { SocketContext } from "../context/context";
import { useRouter } from "next/router";
import { Message, NullMessage } from "../models/message";

function Home() {
	const { socket, message , setMessage , user } = useContext(SocketContext);
	const router = useRouter();
	useEffect(() => {}, [message]);

	const LOGOUT = () =>{
		socket?.webSocket?.close();
		let nullMessage:Message = new NullMessage([],"");
		setMessage(nullMessage);
		router.push("/");
	}

	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center justify-center h-full'>
				<p className="text-3xl">{user?.username}'s personal Space</p>
				<p className="text-xl m-10">Display Name: {user?.displayName}</p>
				<p>Password: {user?.password}</p>
				<div className='flex flex-col justify-center  m-20'>
					<Link href='/friend'>
							<div className='ml-6 mr-2 btn btn-accent btn-wide'>FRIEND</div>
					</Link>
					<Link href='/chatmenu'>
							<div className='m-10 ml-6 mr-2 btn btn-accent btn-wide'>CHAT MENU</div>
					</Link>

					<div className='ml-6 mr-2 btn btn-accent btn-wide' onClick={LOGOUT}>LOG OUT</div>

				</div>
			</div>
			
			
		</div>
	);
}

export default Home;
