import Link from "next/link";
import { useRouter } from "next/router";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import { RegisterFailedMessage, RegisterSuccessMessage } from "../models/message";

function RegisterPage() {
	const router = useRouter();

	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [displayName, setDisplayname] = useState("");
	const { socket, setAuthToken, message ,setUser} = useContext(SocketContext);

	const onUsernameChange = (e: any) => {
		setUsername(e.target.value);
	};

	const onPasswordChange = (e: any) => {
		setPassword(e.target.value);
	};

	const onDisplayNameChange = (e: any) => {
		setDisplayname(e.target.value);
	};

	const register = () => {
		const body: string = JSON.stringify({ username: username, password: password, displayName: displayName});
		socket?.send("/register", body, "POST", "");
	};

	useEffect(() => {
		if (message instanceof RegisterSuccessMessage) {
			alert("Register Success!");
			router.push("auth");
		} else if (message instanceof RegisterFailedMessage) {
			alert("REGISTER FAILED! Username already in used");
		}
	}, [message]);


	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center justify-center h-full'>
				<div>
					<div>
						<label htmlFor='username' className='label'>
							UserName:
						</label>
						<input id='username' className='input input-bordered' type='text' onChange={onUsernameChange} />
					</div>
					<div>
						<label htmlFor='username' className='label'>
							Password:
						</label>
						<input id='password' className='input input-bordered' type='text' onChange={onPasswordChange} />
					</div>
					<div>
						<label htmlFor='username' className='label'>
							DisplayName:
						</label>
						<input id='displayName' className='input input-bordered' type='text' onChange={onDisplayNameChange} />
					</div>
				</div>
				<div className='m-10 btn btn-accent btn-wide' onClick={register}>
					REGISTER
				</div>
				<Link href='/auth'>
					<div className='btn btn-wide'>BACK</div>
				</Link>
			</div>
		</div>
	);
}

export default RegisterPage;
