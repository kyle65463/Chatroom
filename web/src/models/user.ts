export interface User {
	username: string;
	displayName: string;
	password: string;
}

export interface Friend {
	username: string;
	displayName: string;
}

export interface ChatRoom {
	id: string;
	name: string;
	usernames: string[];
}
