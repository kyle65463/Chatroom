import React, { useState } from "react";
import { Manager, Socket } from "socket.io-client";

function Home() {
	const [socket, setSocket] = useState<Socket>();

	const initSocket = () => {
		const manager = new Manager("localhost:9998");
		const socket = manager.socket("/");
		socket.onAny((message) => {});
		setSocket(socket);
	};

	return (
		<div>
			<div className='btn primary' onClick={initSocket}>
				connect
			</div>
		</div>
	);
}

export default Home;
