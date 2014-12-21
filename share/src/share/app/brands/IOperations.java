package share.app.brands;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.brands.Operations:";
	public static final String GET_BRANDS = IOperations.BASE_OPERATIONS + "getBrands";
	public static final String ADD_BRAND = IOperations.BASE_OPERATIONS + "addBrand";
	public static final String EDIT_BRAND = IOperations.BASE_OPERATIONS + "editBrand";
	public static final String DELETE_BRAND = IOperations.BASE_OPERATIONS + "deleteBrand";

	public Brand[] getBrands();

	public Brand addBrand(Brand brand);

	public Boolean editBrand(Brand original, Brand newBrand);

	public Boolean deleteBrand(Brand brand);
}