import Link from "next/link";
import React, { useContext, useEffect } from "react";
import { SocketContext } from "../context/context";

function ChatMenu() {
	const { socket, authToken ,message, user } = useContext(SocketContext);


	useEffect(() => {}, [message]);

	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center justify-center h-full'>
				<p>My chat menu</p>
			</div>

			



			<Link href='/home'>
					<div className='btn btn-wide'>BACK</div>
			</Link>
		</div>
	);
}

export default ChatMenu;
