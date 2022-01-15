import { useRouter } from "next/router";
import React, { useContext, useState } from "react";
import { SocketContext } from "../context/context";

function connect() {

    const router = useRouter();
    const { socket, setSocket } = useContext(SocketContext);
    
    const LOGIN = () => {
        router.push('/login');
    }

    return (
        <div className='flex flex-col justify-center m-20'>
           <div className='btn ml-6 mr-2 btn-accent m-20' onClick={LOGIN}>
				LOGIN
			</div>
            <div className='btn ml-6 mr-2 btn-accent'>
				REGISTER
			</div>
        </div>
    )
}

export default connect
