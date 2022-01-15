import { useRouter } from "next/router";
import React from "react";

function connect() {
	const router = useRouter();

	const LOGIN = () => {
		router.push("/login");
	};

	const REGISTER = () => {
		router.push("/register");
	};

	return (
		<div className='flex flex-col justify-center m-20'>
			<div className='m-20 ml-6 mr-2 btn btn-accent' onClick={LOGIN}>
				LOGIN
			</div>
			<div className='ml-6 mr-2 btn btn-accent' onClick={REGISTER}>
				REGISTER
			</div>
		</div>
	);
}

export default connect;
