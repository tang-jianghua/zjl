/**
 * 树形插件
 */
var ZtreeTool = function(ztreeId){
	this.ztreeId = ztreeId;           //树形控件Id
	this.setting = null;              //属性配置
	this.allData = null;              //所有数据
	this.ztree = null;                //属性控件对象
	
	this.checkEnable = true;                       	   //是否显示复选框
	this.checkChkboxType = {"Y":"ps", "N":"ps"};   	   //父子关联      

	
	//配置属性
	this.setParam = function(){
		this.setting = {  
		        check: {    /**复选框**/  
		            enable: this.checkEnable,                     //显示复选框
		            chkboxType: this.checkChkboxType  			  //勾上：父子关联，取消：父子关联
		        },  
			    view: { 
			        // fontCss: {color:"red"},                                   
			        expandSpeed: "fast",   //设置树展开的动画速度  
			        showIcon:false
			    },                            
			    data: { 
			        key:{
			        	title: "name",
			        	name: "name"
			        },                                   
			        simpleData: {   //简单的数据源                           
				        enable: true,  
				        idKey: "id",     //当前节点的ID  
				        pIdKey: "pid",   //当前节点的父节点ID
				        rootPId: null    //根节点的ID
			        }                            
			    },
			    callback: {     /**回调函数的设置**/  
			        //beforeClick: this.beforeClick,                                    
			        //onCheck: this.onCheck,
			    	//onClick: this.taskOnClick
			    }
	        };	
	};
	this.beforeClick = function(treeId,treeNode){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.checkNode(treeNode,!treeNode.checked,null,true);
		return false;
	};
	
	//勾选
	this.onCheck = function(e,treeId,treeNode){
		var checkedNodes = null; // 选中的节点对象
		var value = "";       
		
		checkedNodes = choseZtree.ztree.getCheckedNodes(true);
		$.each(checkedNodes, function(index, OneObj){
			value += (checkedNodes[index].name+";");
		});
		
		console.log("选中的对象："+value);
	};
	
	//显示
	this.showTree = function(allData){
		this.allData = allData;
		this.ztree = $.fn.zTree.init($("#"+this.ztreeId), this.setting, this.allData);
	};
	
};

