import { useRouter } from "next/router";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import { ListFriendMessage, LoginSuccessMessage } from "../models/message";

function connect() {
	const router = useRouter();

	const LOGIN = () => {
		router.push("/login");
	};

    const REGISTER = () => {
        router.push('/register');
    }

    return (
        <div className='flex flex-col justify-center m-20'>
           <div className='btn ml-6 mr-2 btn-accent m-20' onClick={LOGIN}>
				LOGIN
			</div>
            <div className='btn ml-6 mr-2 btn-accent' onClick={REGISTER}>
				REGISTER
			</div>
        </div>
    )

}

export default connect;
