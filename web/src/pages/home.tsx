import { useRouter } from "next/router";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";

function Home() {
	const router = useRouter();
	const { message } = useContext(SocketContext);

	const BACK = () => {
		router.push("/connect");
	};

	useEffect(() => {}, [message]);

	return (
		<div className='h-screen'>
			<div className='flex flex-col justify-center items-center h-full'>
				<p>My personal Space</p>
			</div>
		</div>
	);
}

export default Home;
