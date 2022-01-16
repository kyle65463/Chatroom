export abstract class ChatMessage{
    constructor(id:string,type:string,sender:string,roomID:string,body:any){
        this.id = id;
        this.type = type;
        this.sender = sender;
        this.roomID = roomID;
        this.body = body;
    }
    public id:string;
    public type:string;
    public sender:string;
    public roomID:string;
    public body:any;

    public static parse(messages:any[]):chatmessage[]{
        for(let i = 0;i < messages.length;i++){
            
        }
        return [];
    }
}



export interface chatmessage {
	id: string;
	sender: string;
	body: any;
    roomID: string;
}

export interface Textmessage extends chatmessage {
	id: string;
	sender: string;
	body: any;
    roomID: string;
}

export interface Filemessage extends chatmessage {
	id: string;
	sender: string;
	body: any;
    roomID: string;
}

