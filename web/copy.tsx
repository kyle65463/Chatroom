import Link from "next/link";
import Router, { useRouter } from "next/router";
import React, { useContext, useState } from "react";
import { SocketContext } from "../context/context";
import { UseSocketContext} from "../context/context"

function Home() {

	const router = useRouter();

	const { socket, setSocket } = useContext(SocketContext);
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	const onUsernameChange = (e: any) => {
		setUsername(e.target.value);
	};

	const onConfirm = () => {
		const newsocket = new WebSocket("ws://localhost:12200");
		newsocket.onopen = () =>{
			console.log("open connection");
			let body:string = JSON.stringify({ username: "luca", password: "luca0514" });
			let header:string = "POST" + " " + "/login" + " HTTP/1.1\r\n" +
                        "Host:localhost\r\n" +
                        "Content-Length: " + body.length + "\r\n" + "\r\n";
			newsocket.send(header + body);
		}
		newsocket.onmessage = (event) =>{
			console.log(event.data);
		}
		setSocket(newsocket);
		router.push('/connect');
		
		console.log(username);
	};



	return (
		<div className='h-screen'>
			<div className='flex flex-col justify-center items-center h-full'>
				<div className='flex items-end'>
					<div>
						<label htmlFor='username' className='label'>
							Username:
						</label>
						<input id='username' className='input input-bordered' type='text' onChange={onUsernameChange}/>
					</div>
					<div className='btn ml-6 mr-2 btn-accent' onClick={onConfirm}>
						Confirm
					</div>
					<div className='btn'>
						BTN
					</div>
				</div>

				<div className='pt-20'>
					<p>Your input in real time:</p>
					<p>{username}</p>
				</div>
			</div>
		</div>
	);
}

export default Home;
