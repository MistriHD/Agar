package Agar;

import java.util.ArrayList;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import apcs.Window;



public class Game {
	
	static Firebase server = new Firebase("https://agarjava.firebaseio.com/");

	public static void main(String[] args) {
		Window.size(800, 600);
		Window.setFrameRate(30);



		final ArrayList<Player> players = new ArrayList<Player>();

		server.child("online").child("KIM-JON-UN---from---NORTH-KOREA").setValue(true);
		server.child("online").child("KIM-JON-UN---from---NORTH-KOREA").onDisconnect().setValue(false);

		Player p = new Player("KIM-JON-UN---from---NORTH-KOREA");

		server.child("online").addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildAdded(DataSnapshot data, String _) {
				String name = data.getKey();
				players.add(new Player(name));
				
			}

			@Override
			public void onChildChanged(DataSnapshot data, String _) {
				String name = data.getKey();
				if ((Boolean) data.getValue()) {
					System.out.println(name + " is online");
				}
				else {
					System.out.println(name + " is not online");
				}
			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub

			}

		});

		server.child("KIM-JON-UN---from---NORTH-KOREAx").setValue(p.x);
		server.child("KIM-JON-UN---from---NORTH-KOREAy").setValue(p.y);
		server.child("KIM-JON-UN---from---NORTH-KOREAr").setValue(p.radius);
		server.child("KIM-JON-UN---from---NORTH-KOREAn").setValue(p.name);

		ArrayList <Blob> blobs = new ArrayList <Blob> ();


		for (int i = 0 ; i < 10000 ; i++) {
			blobs.add(new Blob());
		}


		while (true) {
			Window.out.background(250,250,250);
			for (int i = 0; i < Window.width() + 2; i += 31) {
				for (int j = 0; j < Window.width() + 1; j += 31) {
					Window.out.color("white");
					Window.out.square(i - 2, j - 3, 30);
				}

			}
			
			for (int i = 0; i < players.size(); i++) {
				players.get(i).draw(p.x, p.y);
				
				if (p.checkCollision(players.get(i))) {
					if (p.radius > players.get(i).radius) {
						p.radius += players.get(i).radius;
						players.remove(i);
						i--;
					}
//					else {
//						p = new Player("KIM-JON-UN---from---NORTH-KOREA");
//					}
				}
			}

			p.draw();

			if (p.radius > 100) {
				p.radius = p.radius * 9 / 10;
			}
			
			for (int i = 0 ; i < blobs.size() ; i++) {
				blobs.get(i).draw(p.x, p.y);
				
				if (p.checkCollision(blobs.get(i))) {
					blobs.remove(i);
					p.radius += 1;
					i--;
				}
			}

			if (p.x > 9600) {
				Window.out.color("black");
				Window.out.square(10800 - p.x, 400, 800);
			}

			p.move();
			

			server.child("KIM-JON-UN---from---NORTH-KOREAx").setValue(p.x);
			server.child("KIM-JON-UN---from---NORTH-KOREAy").setValue(p.y);
			server.child("KIM-JON-UN---from---NORTH-KOREAr").setValue(p.radius);

			Window.frame();
		}
	}

}