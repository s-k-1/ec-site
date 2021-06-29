let search =  (event) => {
	let userName = $('form#addUserForm input[name=userName]').val();
	let password = $('form#addUserForm input[name=password]').val();
	let fullName = $('form#addUserForm input[name=fullName]').val();

	if(userName ==""){
		alert("User nameを入力してください。");
			return false;
	}

	if(password==""){
		alert("Passwordを入力してください。");
			return false;
	}
		
	if(fullName==""){
		alert("Full nameを入力してください。");
			return false;
	}

	$.ajax({
		type: 'POST',
		url: '/ecsite/duplicatedUserName',
		data: JSON.stringify({'userName': userName}),
		contentType: 'application/json',
		//datatype: 'json',
	})
	.then((result) => {
		if(result){
			alert('既に使われているため、使用できません。');
		} else {
			let jsonString = {
				'userName': userName,
				'password': password,
				'fullName': fullName
				};
				
		//↓登録時
			$.ajax({
				type: 'POST',
				url: '/ecsite/api/addUser',
				data: JSON.stringify(jsonString),
				contentType: 'application/json',
				//datatype: 'json',
				scriptcharset: 'utf-8'
			})
			.then((result) => {
				alert('登録されました');
				location.replace('/ecsite/admin/');
			}, () => {
				aleart('Error: ajax connection failed.');
			});
		}
	}, () => {
		aleart('Error: ajax connection failed.');
	});
};
			
			/*'result' is declared but its value is never read
				'result'が宣言されていますが、その値が読み取られることはありません
			duplicatedUserNameは完了。重複チェック成功。
			しかし1回目新規登録→失敗。2回目新規登録→成功。以降の登録は成功。
			なお、３４行目～から反応しない。
			Remove unused declaration for:'result'・・・'result'の未使用の宣言を削除します
			Prefix 'result' with an underscore・・・接頭辞「結果」にアンダースコアを付ける
			Convert to anonymous function・・・匿名関数に変換する
			*/
				
				
				