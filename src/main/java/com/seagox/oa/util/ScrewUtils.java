package com.seagox.oa.util;

public class ScrewUtils {

//	public static void main(String[] args) {
//		//数据源
//	    HikariConfig hikariConfig = new HikariConfig();
//	    hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//	    hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/oa");
//	    hikariConfig.setUsername("root");
//	    hikariConfig.setPassword("123456");
//	    //设置可以获取tables remarks信息
//	    hikariConfig.addDataSourceProperty("useInformationSchema", "true");
//	    hikariConfig.setMinimumIdle(2);
//	    hikariConfig.setMaximumPoolSize(5);
//	    DataSource dataSource = new HikariDataSource(hikariConfig);
//	    
//		// 生成文件配置
//		EngineConfig engineConfig = EngineConfig.builder()
//		// 生成文件路径，自己mac本地的地址，这里需要自己更换下路径
//		.fileOutputDir("/Users/X/Desktop")
//		// 打开目录
//		.openOutputDir(false)
//		// 文件类型
//		.fileType(EngineFileType.WORD)
//		// 生成模板实现
//		.produceType(EngineTemplateType.freemarker)
//		//自定义文件名称
//        .fileName("数据库表结构文档").build();
//
//		// 生成文档配置（包含以下自定义版本号、描述等配置连接）
//		Configuration config = Configuration.builder()
//			.version("1.0.0")
//			.title("数据库表结构文档")
//			.description("数据库表设计描述")
//			.dataSource(dataSource).
//			engineConfig(engineConfig).
//			produceConfig(getProcessConfig())
//			.build();
//
//		// 执行生成
//		new DocumentationExecute(config).execute();
//	}
//
//	/**
//	 * 配置想要生成的表+ 配置想要忽略的表
//	 * 
//	 * @return 生成表配置
//	 */
//	public static ProcessConfig getProcessConfig() {
//		// 忽略表名
//		//List<String> ignoreTableName = Arrays.asList("aa", "test_group");
//		// 忽略表前缀，如忽略a开头的数据库表
//		//List<String> ignorePrefix = Arrays.asList("a", "t");
//		// 忽略表后缀
//		//List<String> ignoreSuffix = Arrays.asList("_test", "czb_");
//
//		return ProcessConfig.builder()
//		// 根据名称指定表生成
//		.designatedTableName(new ArrayList<>())
//		// 根据表前缀生成
//		.designatedTablePrefix(new ArrayList<>())
//		// 根据表后缀生成
//		.designatedTableSuffix(new ArrayList<>())
//		// 忽略表名
//		.ignoreTableName(new ArrayList<>())
//		// 忽略表前缀
//		.ignoreTablePrefix(new ArrayList<>())
//		// 忽略表后缀
//		.ignoreTableSuffix(new ArrayList<>()).build();
//	}
}
