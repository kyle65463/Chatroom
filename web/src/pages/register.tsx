import { useRouter } from "next/router";
import React, { useContext, useState } from "react";
import { SocketContext } from "../context/context";

function login() {

    const router = useRouter();
    const { socket, setSocket } = useContext(SocketContext);
    
    const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

    const onUsernameChange = (e: any) => {
		setUsername(e.target.value);
	};

    const onPasswordChange = (e: any) => {
		setPassword(e.target.value);
	};

    const REGISTER = () => {
        ////////////////////////
        alert('this account: ' + username + ' already in used ');
        router.push('connect');
    }

    const BACK = () =>{
        router.push('/connect');
    }

    return (
        <div className='h-screen'>
			<div className='flex flex-col justify-center items-center h-full'>
                <div>
					<div>
						<label htmlFor='username' className='label'>
							Username:
						</label>
						<input id='username' className='input input-bordered' type='text' onChange={onUsernameChange}/>
					</div>
                    <div>
						<label htmlFor='username' className='label'>
							Password:
						</label>
						<input id='username' className='input input-bordered' type='text' onChange={onPasswordChange}/>
					</div>
                </div>
                <div className='btn m-10 btn-accent btn-wide' onClick={REGISTER}>
					REGISTER
				</div>
				<div className='btn btn-wide' onClick={BACK}>
					BACK
				</div>
                <div className='pt-20'>
					<p>Your input in real time:</p>
					<p>{username}</p>
                    <p>{password}</p>
				</div>
			</div>
		</div>
    )
}

export default login
