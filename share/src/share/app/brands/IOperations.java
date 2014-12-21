package share.app.brands;

public interface IOperations
{
	public static final String GET_BRANDS = "server.app.brands.Operations:getBrands";
	public static final String ADD_BRAND = "server.app.brands.Operations:addBrand";
	public static final String EDIT_BRAND = "server.app.brands.Operations:editBrand";
	public static final String DELETE_BRAND = "server.app.brands.Operations:deleteBrand";
	
	public Brand[] getBrands();
	
	public Brand addBrand(Brand brand);
	
	public Boolean editBrand(Brand original, Brand newBrand);
	
	public Boolean deleteBrand(Brand brand);
}