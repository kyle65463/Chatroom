import Link from "next/link";
import Router, { useRouter } from "next/router";
import React, { useContext, useState } from "react";
import { SocketContext } from "../context/context";
import { UseSocketContext} from "../context/context"

function Home() {

	const router = useRouter();

	const { socket, setSocket } = useContext(SocketContext);

	const initSocket = () => {
		const newsocket = new WebSocket("ws://localhost:12200");
		newsocket.onopen = () =>{
			console.log("open connection");
		}
		newsocket.onmessage = (event) =>{
			console.log(event.data);
		}
		setSocket(newsocket);
		router.push('/connect');
		
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
