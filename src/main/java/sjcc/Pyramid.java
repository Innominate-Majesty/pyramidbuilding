package sjcc;

// pyramid class, that corresponds to the information in the json file
public class Pyramid {

  protected Integer id;
  protected String name;
  protected String[] contributors;

  // constructor
  public Pyramid(
    Integer pyramidId,
    String pyramidName,
    String[] pyramidContributors
  ) {
    id = pyramidId;
    name = pyramidName;
    contributors = pyramidContributors;
  }

  public void printDetails() {
    System.out.println("********************************************************************************");
    System.out.println("Pyramid Name: " + name);
    System.out.println("Pyramid ID: " + id);
    if (contributors != null) {
      for (int i = 0; i < contributors.length; i++) {
        System.out.println("Contributor " + (i + 1) + ": " + contributors[i]);
      }
    }
    System.out.println("********************************************************************************");
  }
}
