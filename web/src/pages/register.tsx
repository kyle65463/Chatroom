import Link from "next/link";
import { useRouter } from "next/router";
import React, { useState } from "react";

function RegisterPage() {
	const router = useRouter();

	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	const onUsernameChange = (e: any) => {
		setUsername(e.target.value);
	};

	const onPasswordChange = (e: any) => {
		setPassword(e.target.value);
	};

	const register = () => {
		alert("this account: " + username + " already in used ");
		router.push("auth");
	};

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
						<input id='username' className='input input-bordered' type='text' onChange={onPasswordChange} />
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
