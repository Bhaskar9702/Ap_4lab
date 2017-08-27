import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

class Herbivore extends Animal{
	int grasscap;
	public Herbivore(String s,double x,double y,int h,int h1,int t){
		this.name=s;
		this.x=x;
		this.y=y;
		this.health=h;
		this.grasscap=h1;
		this.timestamp=t;
	}
}
class Carnivore extends Animal{
	public Carnivore(String s,int x,int y,int h,int t){
		this.name=s;
		this.x=x;
		this.y=y;
		this.health=h;
		this.timestamp=t;
	}
}
class Animal extends World{
	int timestamp;
	int health;
	String name;
	int count1=0;
	int count2=0;
	int count3=0;
	int count4=0;
	public Animal[] enque(Animal[] animal,Animal r){
		Animal[] temp=new Animal[animal.length+1];
		int x=0;
		for(int i=0;i<animal.length;i++){
				temp[i]=animal[x];
		}
		temp[temp.length-1]=r;
		return temp;
	}
	public Animal[] deque(Animal[] animal,int r){
		Animal[] temp=new Animal[animal.length-1];
		int x=0;
		for(int i=0;i<animal.length;i++){
			if(i!=r){
				temp[x]=animal[i];
				x++;
			}
		}
		return temp;
	}
	public int nearest_carnivore(Animal a,Animal[] l){
		double dist=0;
		int index=0;
		for(int i=0;i<l.length;i++){
			if(l[i] instanceof Carnivore){
				double dist1=Math.sqrt((l[i].x-a.x)*(l[i].x-a.x)+(l[i].y-a.y)*(l[i].y-a.y));
				if(dist1<dist){
					dist=dist1;
					index=i;
				}
			}
		}
		return index;
	}
	public int nearest_herbivore(Animal a,Animal[] l){
		double dist=0;
		int index=0;
		for(int i=0;i<l.length;i++){
			if(l[i] instanceof Herbivore){
				double dist1=Math.sqrt((l[i].x-a.x)*(l[i].x-a.x)+(l[i].y-a.y)*(l[i].y-a.y));
				if(dist1<dist){
					dist=dist1;
					index=i;
				}
			}
		}
		return index;
	}
	public Animal[] Simulate_Carnivore(int h,grassland[] l,Animal[] animal){
		int q;
		int de;
		if(this.name.equals("First Carnivore")){
			q=count3;
			de=1;
		}
		else{
			q=count4;
			de=2;
		}
		if(h!=0){
			int near=nearest_herbivore(this,animal);
			Animal Her=animal[near];
			double dist1=Math.sqrt((Her.x-this.x)*(Her.x-this.x)+(Her.y-this.y)*(Her.y-this.y));
			if(dist1<1){
				deque(animal,near);
				this.health+=(2/3)*Her.health;
			}	
			else{
				grassland f=new grassland(0,0,0,0);
				int check=f.check_grassland(this, l);
				if(check==0){
					Random r=new Random();
					int at=r.nextInt(100)+1;
					if(at<=92){
						double dist=Math.sqrt((Her.x-this.x)*(Her.x-this.x)+(Her.y-this.y)*(Her.y-this.y));
						double m=4;
						double n=dist-m;
						this.x=((m*Her.x)+(n*this.x))/(m-n);
						this.y=((m*Her.y)+(n*this.y))/(m-n);
					}
					else{
						this.health-=60;
					}
				}
				else{
					Random r=new Random();
					int at=r.nextInt(100)+1;
					if(at<=75){
						double dist=Math.sqrt((Her.x-this.x)*(Her.x-this.x)+(Her.y-this.y)*(Her.y-this.y));
						double m=2;
						double n=dist-m;
						this.x=((m*Her.x)+(n*this.x))/(m-n);
						this.y=((m*Her.y)+(n*this.y))/(m-n);
					}
					else{
						this.health-=30;
					}
				}
			}
		}
		else{
			q++;
			if(de==1){
				count3+=q;
				if(count3>7){
					this.health-=5;
				}
			}
			else{
				count4+=q;
				if(count4>7){
					this.health-=5;
				}
			}
			
		}
		if(this.health>0){
			System.out.println("It is "+this.name);
			System.out.println("It's health after taking turn is "+this.health);
		}
		else{
			System.out.println("It is "+this.name);
			System.out.println("It is dead");
			count_c-=1;
			for(int i=0;i<animal.length;i++){
				if(this.name.equals(animal[i].name)){
					Animal[] k=animal;
					animal=deque(k,i);
				}
			}
		}
		return animal;
	}
	public Animal[] Simulate_Herbivore(int c,grassland[] l,Animal[] animal,Herbivore[] herb){
		int qs;
		int de;
		if(this.name.equals("First Herbivore")){
			qs=count1;
			de=1;
		}
		else{
			qs=count2;
			de=2;
		}
		if(c>0){
			Animal s=this;
			grassland f=new grassland(0,0,0,0);
			int q=f.check_grassland(s,l);
			if(q==0){
				Random r=new Random();
				int at=r.nextInt(100)+1;
				if(at>5){
					int a=r.nextInt(100)+1;
					if(a<=65){
						grassland g1=l[0];
						grassland g2=l[1];
						double dist1=Math.sqrt((g1.x-this.x)*(g1.x-this.x)+(g1.y-this.y)*(g1.y-this.y));
						double dist2=Math.sqrt((g2.x-this.x)*(g2.x-this.x)+(g2.y-this.y)*(g2.y-this.y));
						if(dist1>dist2){
							grassland temp=l[1];
							double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
							double m=5;double n=dist-m;
							this.x=((m*temp.x)+(n*this.x))/(m+n);
							this.y=((m*temp.y)+(n*this.y))/(m+n);
						}
						else{
							grassland temp=l[0];
							double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
							double m=5;double n=dist-m;
							this.x=((m*temp.x)+(n*this.x))/(m+n);
							this.y=((m*temp.y)+(n*this.y))/(m+n);
						}
					}
					else{
						int in=nearest_carnivore(this,animal);
						double dist=Math.sqrt((animal[in].x-this.x)*(animal[in].x-this.x)+(animal[in].y-this.y)*(animal[in].y-this.y));
						double n=4;
						double m=dist+n;
						this.x=((m*animal[in].x)-(n*this.x))/(m-n);
						this.y=((m*animal[in].y)-(n*this.y))/(m-n);
					}
				}
				else{
					qs++;
					if(de==1){
						count1+=qs;
						if(count1>7){
							this.health-=5;
						}
					}
					else{
						count2+=qs;
						if(count2>7){
							this.health-=5;
						}
					}
				}
			}
			else{
				int w=0;
				for(int i=0;i<herb.length;i++){
					if(this.x==herb[i].x && this.y==herb[i].y && this.timestamp==herb[i].timestamp){
						w=herb[i].grasscap;
					}
				}
				Herbivore h=new Herbivore(this.name,this.x,this.y,this.health,w,this.timestamp);
				Random r=new Random();
				int at=r.nextInt(100)+1;
				if(q==1){
					if(h.grasscap<=l[0].Grass){
						if(at<=10){
							int t=r.nextInt(100)+1;
							if(t<=50){
								grassland temp=l[1];
								double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
								double m=3;double n=dist-m;
								this.x=((m*temp.x)+(n*this.x))/(m+n);
								this.y=((m*temp.y)+(n*this.y))/(m+n);
								this.health-=25;
							}
							else{
								int in=nearest_carnivore(this,animal);
								double dist=Math.sqrt((animal[in].x-this.x)*(animal[in].x-this.x)+(animal[in].y-this.y)*(animal[in].y-this.y));
								double n=2;
								double m=dist+n;
								this.x=((m*animal[in].x)-(n*this.x))/(m-n);
								this.y=((m*animal[in].y)-(n*this.y))/(m-n);
							}
						}
						else{
							l[0].Grass-=h.grasscap;
							this.health=this.health+this.health/2;
						}
					}
					else{
						if(at<=80){
							int t=r.nextInt(100)+1;
							if(t<=30){
								grassland temp=l[1];
								double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
								double m=2;double n=dist-m;
								this.x=((m*temp.x)+(n*this.x))/(m+n);
								this.y=((m*temp.y)+(n*this.y))/(m+n);
								this.health-=25;
							}
							else{
								int in=nearest_carnivore(this,animal);
								double dist=Math.sqrt((animal[in].x-this.x)*(animal[in].x-this.x)+(animal[in].y-this.y)*(animal[in].y-this.y));
								double n=4;
								double m=dist+n;
								this.x=((m*animal[in].x)-(n*this.x))/(m-n);
								this.y=((m*animal[in].y)-(n*this.y))/(m-n);
							}
						}
						else{
							l[0].Grass-=h.grasscap;
							this.health=this.health+this.health/5;
						}
					}
				}
				else{
					if(h.grasscap<=l[1].Grass){
						if(at<=10){
							int t=r.nextInt(100)+1;
							if(t<=50){
								grassland temp=l[0];
								double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
								double m=3;double n=dist-m;
								this.x=((m*temp.x)+(n*this.x))/(m+n);
								this.y=((m*temp.y)+(n*this.y))/(m+n);
								this.health-=25;
							}
							else{
								int in=nearest_carnivore(this,animal);
								double dist=Math.sqrt((animal[in].x-this.x)*(animal[in].x-this.x)+(animal[in].y-this.y)*(animal[in].y-this.y));
								double n=2;
								double m=dist+n;
								this.x=((m*animal[in].x)-(n*this.x))/(m-n);
								this.y=((m*animal[in].y)-(n*this.y))/(m-n);
							}
						}
						else{
							l[0].Grass-=h.grasscap;
						}
					}
					else{
						if(at<=80){
							int t=r.nextInt(100)+1;
							if(t<=30){
								grassland temp=l[0];
								double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
								double m=2;double n=dist-m;
								this.x=((m*temp.x)+(n*this.x))/(m+n);
								this.y=((m*temp.y)+(n*this.y))/(m+n);
								this.health-=25;
							}
							else{
								int in=nearest_carnivore(this,animal);
								double dist=Math.sqrt((animal[in].x-this.x)*(animal[in].x-this.x)+(animal[in].y-this.y)*(animal[in].y-this.y));
								double n=4;
								double m=dist+n;
								this.x=((m*animal[in].x)-(n*this.x))/(m-n);
								this.y=((m*animal[in].y)-(n*this.y))/(m-n);
							}
						}
						else{
							l[0].Grass-=h.grasscap;
							this.health=this.health+this.health/5;
						}
					}
				}
			}
		}
		else{
			Animal s=this;
			grassland f=new grassland(0,0,0,0);
			int q=f.check_grassland(s,l);
			if(q==0){
				Random r=new Random();
				int at=r.nextInt(1);
				if(at==0){
					grassland g1=l[0];
					grassland g2=l[1];
					double dist1=Math.sqrt((g1.x-this.x)*(g1.x-this.x)+(g1.y-this.y)*(g1.y-this.y));
					double dist2=Math.sqrt((g2.x-this.x)*(g2.x-this.x)+(g2.y-this.y)*(g2.y-this.y));
					if(dist1>dist2){
						grassland temp=l[1];
						double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
						double m=5;double n=dist-m;
						this.x=((m*temp.x)+(n*this.x))/(m+n);
						this.y=((m*temp.y)+(n*this.y))/(m+n);
					}
					else{
						grassland temp=l[0];
						double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
						double m=5;double n=dist-m;
						this.x=((m*temp.x)+(n*this.x))/(m+n);
						this.y=((m*temp.y)+(n*this.y))/(m+n);
					}
				}
			}
			else{
				if(q==1){
					grassland temp=l[1];
					double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
					double m=5;double n=dist-m;
					this.x=((m*temp.x)+(n*this.x))/(m+n);
					this.y=((m*temp.y)+(n*this.y))/(m+n);
					this.health-=25;
				}
				else{
					grassland temp=l[0];
					double dist=Math.sqrt((temp.x-this.x)*(temp.x-this.x)+(temp.y-this.y)*(temp.y-this.y));
					double m=5;double n=dist-m;
					this.x=((m*temp.x)+(n*this.x))/(m+n);
					this.y=((m*temp.y)+(n*this.y))/(m+n);
					this.health-=25;
				}
			}
		}
		if(this.health>0){
			System.out.println("It is "+this.name);
			System.out.println("Its health after taking turn is "+this.health);
		}
		else{
			System.out.println("It is "+this.name);
			System.out.println("It is dead");
			count_h-=1;
			for(int i=0;i<animal.length;i++){
				if(this.name.equals(animal[i].name)){
					Animal[] k=animal;
					animal=deque(k,i);
				}
			}
		}
		return animal;
	}
}
class grassland extends World{
	int radius;
	int Grass;
	public grassland(int x1,int y1,int radius1, int grass1){
		this.x=x1;
		this.y=y1;
		this.radius=radius1;
		this.Grass=grass1;
	}
	public int check_grassland(Animal a,grassland[] l){
		grassland g1=l[0];
		grassland g2=l[1];
		double dist1=Math.sqrt((g1.x-a.x)*(g1.x-a.x)+(g1.y-a.y)*(g1.y-a.y));
		double dist2=Math.sqrt((g2.x-a.x)*(g2.x-a.x)+(g2.y-a.y)*(g2.y-a.y));
		if(dist1<g1.radius){
			return 1;
		}
		else if(dist2<g2.radius){
			return 2;
		}
		else{
			return 0;
		}
	}
	
}
public class World {
	double x;double y;
	public static int count_h=2;
	public static int count_c=2;
	public static Animal[] priority(Animal[] l){
		for(int i=0;i<l.length-1;i++){
			for(int j=i+1;j<l.length;j++){
				int comp=compare(l[i],l[j]);
				if(comp==0){
					Animal temp=l[i];
					l[i]=l[j];
					l[j]=temp;
				}
			}
		}
		return l;
	}
	public static int compare(Animal a,Animal b){
		if(a.timestamp==b.timestamp){
			if(a.health==b.health){
				if(a instanceof Herbivore && b instanceof Herbivore){
					double dis1=Math.sqrt((double)(a.x*a.x+a.y*a.y));
					double dis2=Math.sqrt((double)(b.x*b.x+b.y*b.y));
					if(dis1>dis2){
						return 0;
					}
					else if(dis1<dis2){
						return 1;
					}
					else{
						return 0;
					}
				}
				else{
					if(a instanceof Herbivore && b instanceof Carnivore){
						return 0;
					}
					else if(a instanceof Carnivore && b instanceof Herbivore){
						return 1;
					}
					else{
						double dis1=Math.sqrt((double)(a.x*a.x+a.y*a.y));
						double dis2=Math.sqrt((double)(b.x*b.x+b.y*b.y));
						if(dis1>dis2){
							return 0;
						}
						else if(dis1<dis2){
							return 1;
						}
						else{
							return 0;
						}
					}
				}
			}
			else{
				if(a.health>b.health){
					return 0;
				}
				else{
					return 1;
				}
			}
		}
		else{
			if(a.timestamp>b.timestamp){
				return 0;
			}
			else{
				return 1;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader inp= new BufferedReader(new InputStreamReader(System.in));
		// TODO Auto-generated method stub
		grassland[] grass=new grassland[2];
		Animal[] animal=new Animal[4];
		Herbivore[] herb=new Herbivore[2];
		System.out.println("Enter Total Final Time for Simulation:");
		String total=inp.readLine();
		System.out.println("Enter x,y center, radius and Grass Available for first Grassland:");
		String a=inp.readLine();
		String[] a1=a.split(" ");
		System.out.println("Enter x,y center, radius and Grass Available for second Grassland:");
		String b=inp.readLine();
		String[] b1=b.split(" ");
		grass[0]=new grassland(Integer.parseInt(a1[0]),Integer.parseInt(a1[1]),Integer.parseInt(a1[2]),Integer.parseInt(a1[3]));
		grass[1]=new grassland(Integer.parseInt(b1[0]),Integer.parseInt(b1[1]),Integer.parseInt(b1[2]),Integer.parseInt(b1[3]));
		System.out.println("Enter Health and Grass Capacity for Herbivores:");
		String c=inp.readLine();
		String[] c1=c.split(" ");
		int h1=Integer.parseInt(c1[0]);
		int h2=Integer.parseInt(c1[1]);
		System.out.println("Enter x, y position and timestamp for First Herbivore:");
		String d=inp.readLine();
		String[] d1=d.split(" ");
		herb[0]=new Herbivore("First Herbivore",Integer.parseInt(d1[0]),Integer.parseInt(d1[1]),h1,h2,Integer.parseInt(d1[2]));
		animal[0]=new Herbivore("First Herbivore",Integer.parseInt(d1[0]),Integer.parseInt(d1[1]),h1,h2,Integer.parseInt(d1[2]));
		System.out.println("Enter x, y position and timestamp for Second Herbivore:");
		String e=inp.readLine();
		String[] e1=e.split(" ");
		herb[1]=new Herbivore("Second Herbivore",Integer.parseInt(e1[0]),Integer.parseInt(e1[1]),h1,h2,Integer.parseInt(e1[2]));
		animal[1]=new Herbivore("Second Herbivore",Integer.parseInt(e1[0]),Integer.parseInt(e1[1]),h1,h2,Integer.parseInt(e1[2]));
		System.out.println("Enter Health for Carnivores:");
		int f=Integer.parseInt(inp.readLine());
		System.out.println("Enter x, y position and timestamp for First Carnivore:");
		String g=inp.readLine();
		String[] g1=g.split(" ");
		animal[2]=new Carnivore("First Carnivore",Integer.parseInt(g1[0]),Integer.parseInt(g1[1]),f,Integer.parseInt(g1[2]));
		System.out.println("Enter x, y position and timestamp for Second Carnivore:");
		String i=inp.readLine();
		String[] i1=i.split(" ");
		animal[3]=new Carnivore("Second Carnivore",Integer.parseInt(i1[0]),Integer.parseInt(i1[1]),f,Integer.parseInt(i1[2]));
		animal=priority(animal);
		int max=0;
		if(max<animal[0].timestamp){
			max=animal[0].timestamp;
		}
		if(max<animal[1].timestamp){
			max=animal[1].timestamp;
		}
		if(max<animal[2].timestamp){
			max=animal[2].timestamp;
		}
		if(max<animal[3].timestamp){
			max=animal[3].timestamp;
		}
		int u=1;
		System.out.println("The Simulation Begins-");
		while (u<=Integer.parseInt(total)){
			if(animal.length>0){
				Animal[] temp1=animal;
				for(int i3=0;i3<temp1.length;i3++){
					if(temp1[i3] instanceof Herbivore){
						animal=temp1[i3].Simulate_Herbivore(count_c,grass,animal,herb);
					}
					else{
						animal=temp1[i3].Simulate_Carnivore(count_h,grass,animal);
					}
					u++;
				}
				Random r=new Random();
				int at=max+r.nextInt(Integer.parseInt(total)-max);
				for(int t=0;t<animal.length;t++){
					animal[t].timestamp=at;
					max=at;
					at=max+r.nextInt(Integer.parseInt(total)-max);
				}
				max=at;
				animal=priority(animal);
			}
		}
	}

}
