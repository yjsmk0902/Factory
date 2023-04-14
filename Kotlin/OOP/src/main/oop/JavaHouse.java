public class JavaHouse {

    private String address;
    private LivingRoom livingRoom;

    public JavaHouse(String address, LivingRoom livingRoom) {
        this.address = address;
        this.livingRoom = new LivingRoom(10);
    }

    public LivingRoom getLivingRoom() {
        return livingRoom;
    }

    //내부 클래스
    public class LivingRoom{
        private double area;

        public LivingRoom(double area) {
            this.area = area;
        }
        //바깥 클래스와 연결되어 있음
        public String getAddress() {
            return JavaHouse.this.address;
        }
    }
}
