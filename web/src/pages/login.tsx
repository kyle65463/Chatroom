import { useRouter } from "next/router";
import React, { useContext, useState } from "react";
import { SocketContext } from "../context/context";

function login() {

    const router = useRouter();
    const { socket, setSocket } = useContext(SocketContext);
    
    const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

    const LOGIN = () => {
        
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
						<input id='username' className='input input-bordered' type='text'/>
					</div>
                    <div>
						<label htmlFor='username' className='label'>
							Password:
						</label>
						<input id='username' className='input input-bordered' type='text'/>
					</div>
                </div>
                    <div className='btn m-10 btn-accent btn-wide'>
						    Confirm
					</div>
					<div className='btn btn-wide' onClick={BACK}>
						BACK
					</div>

			</div>
		</div>
    )
}

export default login
