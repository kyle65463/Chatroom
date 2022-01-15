import { useRouter } from "next/router";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import { LoginSuccessMessage } from "../models/message";

function connect() {
	const router = useRouter();
	const { socket, setSocket } = useContext(SocketContext);

	const LOGIN = () => {
		router.push("/login");
	};

	useEffect(() => {
        if(socket?.message instanceof LoginSuccessMessage) {
            // do sth
        }
	}, [socket?.message]);

	return (
		<div className='flex flex-col justify-center m-20'>
			<div className='btn ml-6 mr-2 btn-accent m-20' onClick={LOGIN}>
				LOGIN
			</div>
			<div className='btn ml-6 mr-2 btn-accent'>REGISTER</div>
		</div>
	);
}

export default connect;
