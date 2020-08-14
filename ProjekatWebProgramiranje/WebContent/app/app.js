var app = new Vue({ 
    el: '#users',
    data: {
        users: null,
        title: "Register",
        mode: "BROWSE",
        registeredUsers: {},
        searchField: ""
    },
    mounted () {
        axios
          .get('rest/users/getalljson')
          .then(response => (this.users = response.data))
    },
    methods: {
		registerUser : function(user){
			axios
    		.post("rest/users/register", user)
    		.then(response => toast('User ' + user.name + " " + user.surname + " uspešno kreiran."));
    		this.mode = 'BROWSE';
		}
    },
    filters: {
    	dateFormat: function (value, format) {
    		var parsed = moment(value);
    		return parsed.format(format);
    	}
   	}
});
