import { SocketAddress } from "net";
import { useRouter } from "next/router";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import { LoginFailedMessage, LoginSuccessMessage } from "../models/message";

function login() {

    const router = useRouter();
    const { socket, setSocket } = useContext(SocketContext);
    

    const BACK = () =>{
        router.push('/connect');
    }

    useEffect(() => {


	}, [socket?.message]);

    return (
        <div className='h-screen'>
			<div className='flex flex-col justify-center items-center h-full'>
                <p>My personal Space</p>
			</div>
		</div>
    )
}

export default login
