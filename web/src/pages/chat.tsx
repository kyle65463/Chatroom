import Link from "next/link";
import React, { useContext, useEffect, useReducer } from "react";
import { SocketContext } from "../context/context";
import { useRouter } from "next/router";
import { Message, NullMessage } from "../models/message";

function Home() {
	const { socket, authToken ,message, user, chatroom } = useContext(SocketContext);
	const router = useRouter();
	useEffect(() => {}, [message]);
    useEffect(()=>{
        if(authToken){
			const body: string = JSON.stringify({id:chatroom?.id,limit:"1"});
            socket?.send("/chat/histories",body,"POST",authToken);
        }
    },[])


	return (
		<div className='h-screen'>

            <Link href='/chatmenu'>
				<div className='m-10 ml-6 mr-2 btn btn-accent btn-wide'>BACK</div>
			</Link>			
		</div>
	);
}

export default Home;
