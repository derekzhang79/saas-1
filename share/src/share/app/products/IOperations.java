package share.app.products;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.products.Operations:";
	public static final String GET_PRODUCTS = IOperations.BASE_OPERATIONS + "getProducts";
	public static final String ADD_PRODUCT = IOperations.BASE_OPERATIONS + "addProduct";
	public static final String EDIT_PRODUCT = IOperations.BASE_OPERATIONS + "editProduct";
	public static final String DELETE_PRODUCT = IOperations.BASE_OPERATIONS + "deleteProduct";
	
	public Product[] getProducts(Long barCode, Integer sectionID, Integer brand);
	
	public Product addProduct(Product product);
	
	public Boolean editProduct(Product original, Product newProduct);
	
	public Boolean deleteProduct(Product product);
}