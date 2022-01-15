import Link from "next/link";
import React from "react";

function AuthPage() {
	return (
		<div>
			<div className='flex items-center justify-center mt-80'>
				<div className='flex flex-col justify-center w-40 m-20'>
					<Link href='/login'>
						<div className='my-5 ml-6 mr-2 btn btn-accent'>LOGIN</div>
					</Link>

					<Link href='/register'>
						<div className='ml-6 mr-2 btn btn-accent'>REGISTER</div>
					</Link>
				</div>
			</div>
		</div>
	);
}

export default AuthPage;
