import Link from "next/link";
import { useRouter } from "next/router";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import { LoginFailedMessage, LoginSuccessMessage } from "../models/message";

function LoginPage() {
	const router = useRouter();
	const { socket, setAuthToken, message ,setUser} = useContext(SocketContext);
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	const onUsernameChange = (e: any) => {
		setUsername(e.target.value);
	};

	const onPasswordChange = (e: any) => {
		setPassword(e.target.value);
	};

	const login = () => {
		const body: string = JSON.stringify({ username: username, password: password });
		socket?.send("/login", body, "POST", "");
	};

	useEffect(() => {
		if (message instanceof LoginSuccessMessage) {
			const token = message.authToken;
			setAuthToken(token);
            const user = message.user;
            setUser(user);
			router.push("home");
		} else if (message instanceof LoginFailedMessage) {
			alert("LOGIN FAILED! Your username or password is wrong");
		}
	}, [message]);

	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center justify-center h-full'>
				<div>
					<div>
						<label htmlFor='username' className='label'>
							Username:
						</label>
						<input id='username' className='input input-bordered' type='text' onChange={onUsernameChange} />
					</div>
					<div>
						<label htmlFor='username' className='label'>
							Password:
						</label>
						<input id='password' className='input input-bordered' type='text' onChange={onPasswordChange} />
					</div>
				</div>
				<div className='m-10 btn btn-accent btn-wide' onClick={login}>
					Confirm
				</div>

				<Link href='/auth'>
					<div className='btn btn-wide'>BACK</div>
				</Link>
			</div>
		</div>
	);
}

export default LoginPage;
