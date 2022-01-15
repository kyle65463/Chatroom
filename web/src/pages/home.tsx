import React, { useContext, useEffect } from "react";
import { SocketContext } from "../context/context";

function Home() {
	const { message, user } = useContext(SocketContext);

	useEffect(() => {}, [message]);

	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center justify-center h-full'>
				<p>My personal Space</p>
			</div>
		</div>
	);
}

export default Home;
