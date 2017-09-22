package main.ehcache.develop;
/**
 * @author momo
 * @time 2017年9月22日下午4:31:32
 * 
 * CacheAsSor Pattern:
 * 在说主题之前先普及几个概念：
 * 1.Write-through： 直写模式,在数据更新时,同时写入缓存Cache和后端存储。此模式的优点是操作简单；缺点是因为数据修改需要同时写入存储，数据写入速度较慢。
 * 2.Write-back(Write-behind):回写模式）在数据更新时只写入缓存Cache。只在数据被替换出缓存时，被修改的缓存数据才会被写到后端存储。
 * 		此模式的优点是数据写入速度快，因为不需要写存储；缺点是一旦更新后的数据未被写入存储时出现系统掉电的情况，数据将无法找回。
 * 3.Read-through:
 * 
 */
public class CacheAsSor {

}
