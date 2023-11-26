package sjcc;

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

  public void printDetailsWithContributors() {
    System.out.println("********************************************************************************\n");
    System.out.printf("Pyramid %s\n\n", name);
    System.out.printf("\tID: %d\n\n", id);
    if (contributors != null) {
      int totalContribution = 0;
      for (String hieroglyphic : contributors) {
        Pharaoh pharaoh = App.findPharaohHieroglyphic(hieroglyphic);
        if (pharaoh != null) {
          System.out.println("\tContributors: " + pharaoh.getName() + " - " + pharaoh.getContribution() + " gold coinss\n");
          totalContribution += pharaoh.getContribution();
        } else {
          System.out.println("Contributor with hieroglyphic " + hieroglyphic + " not found.\n");
        }
      }
      System.out.println("\tTotal contribution: " + totalContribution + " gold coinss\n");
    }
    System.out.println("********************************************************************************\n");
  }
}
