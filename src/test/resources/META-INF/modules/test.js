(function(){
	define(["kissy"],function(Kissy){
		var page = {};
		
		page.init = function(url){
			KISSY.use('node,io', function (S, Node,IO) {
			    // 查找 DOM 节点.
			    var oneElementById     = Node.one('#foo'),
			        oneElementByName   = Node.one('body'),
			        allElementsByClass = Node.all('.bar');

			    // 创建 DOM 节点.
			    var contentNode = new Node('<div>'),
			        listNode    = new Node('<ul>'),
			        footerNode  = new Node('<footer>');

			    // 操作节点，支持链式调用
			    contentNode.html('Hello Kissy!')
			                .append('<p>touch me</p>')
			                .addClass('highlight')
			                .appendTo('body');

			    // 绑定事件
			    Node.one('#close-button').on('click', function (e) {
			        IO({
			        	url:url,
			        	data : {
			        		name : 'flywind'
			        	},
			        	success:function(d){
			        		console.log(d);
			        	}
			        });
			    });
			});
		}
		
		return {
			init : page.init
		}
	});
}).call(this);