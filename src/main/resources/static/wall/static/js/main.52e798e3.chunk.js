(this.webpackJsonpseinasivut=this.webpackJsonpseinasivut||[]).push([[0],{12:function(e,t,n){},13:function(e,t,n){},15:function(e,t,n){},16:function(e,t,n){"use strict";n.r(t);var r=n(0),c=n(1),s=n(6),u=n.n(s),o=n(4),a=(n(12),function(){return Object(r.jsx)("footer",{children:"\xa9 DinkedIn 2020"})}),i=(n(13),n(2)),d=n.n(i),j=n(3),l=document.cookie.split("; ").find((function(e){return e.startsWith("XSRF-TOKEN")})).split("=")[1],h=function(){var e=Object(j.a)(d.a.mark((function e(){var t;return d.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,fetch("/logout",{method:"POST",headers:{"X-XSRF-TOKEN":l}});case 2:return t=e.sent,e.abrupt("return",t);case 4:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),f=function(){var e=Object(j.a)(d.a.mark((function e(t){var n;return d.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,fetch("/accounts/".concat(t));case 2:return n=e.sent,e.abrupt("return",n.json());case 4:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),O=function(){var e=Object(j.a)(d.a.mark((function e(t){var n;return d.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,fetch("/accounts/".concat(t,"/friendrequests"));case 2:return n=e.sent,e.abrupt("return",n.json());case 4:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),b=function(){var e=Object(j.a)(d.a.mark((function e(){var t;return d.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,fetch("/accounts/logged",{headers:{"X-XSRF-TOKEN":l}});case 2:return t=e.sent,e.abrupt("return",t.json());case 4:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),A=function(){var e=Object(j.a)(d.a.mark((function e(t,n){var r;return d.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,fetch("/accounts/friendrequests",{method:"PUT",headers:{Accept:"application/json","Content-Type":"application/json","X-XSRF-TOKEN":l},body:JSON.stringify({from:t,to:n})});case 2:return r=e.sent,e.abrupt("return",r.json());case 4:case"end":return e.stop()}}),e)})));return function(t,n){return e.apply(this,arguments)}}(),p=function(){var e=Object(j.a)(d.a.mark((function e(t,n){var r;return d.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,fetch("/accounts/friendrequests",{method:"DELETE",headers:{Accept:"application/json","Content-Type":"application/json","X-XSRF-TOKEN":l},body:JSON.stringify({from:t,to:n})});case 2:return r=e.sent,e.abrupt("return",r.json());case 4:case"end":return e.stop()}}),e)})));return function(t,n){return e.apply(this,arguments)}}(),x=function(e){e.preventDefault()},m=function(e){var t=e.username,n=e.current,c=t===n?Object(r.jsx)("div",{children:Object(r.jsx)("form",{onSubmit:function(e){return function(e){e.preventDefault(),h().then((function(e){e.ok?window.location.href="/login?logout":alert("Error logging out!")}))}(e)},children:Object(r.jsx)("button",{id:"logoutbutton",type:"submit",children:"Logout"})})}):Object(r.jsxs)("div",{children:[Object(r.jsx)("a",{href:"/join",children:"Join now"}),Object(r.jsx)("a",{id:"loginbutton",href:"/login",children:"Sign in"})]});return Object(r.jsxs)("nav",{children:[Object(r.jsx)("div",{children:Object(r.jsx)("header",{children:Object(r.jsx)("a",{href:"/",children:Object(r.jsx)("img",{id:"logo",src:"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAAAeCAIAAABVOSykAAAPfHpUWHRSYXcgcHJvZmlsZSB0eXBlIGV4aWYAAHjarZlrkuS4DYT/8xQ+Al8gweOQIBnhG/j4/qDS9HheG7Eb7pouVaskPoBEZkITzn/+fcO/+Claa6jStY3WIj911JEnHzR+fj7HFOvz/vy0+n6Xfjwf0nm/yJwqHMt7w3s+Tc7L9xv6O1BaP54P3d5x9B3o/eLbgMVnznx4r9N3oJI/59P7dxjvfbP+z3be37Xfc/I5/Px37QRjC+OVHPIpqUTe1Wcpn9/p53jPpXFRKpXPwmuWUurvYxe+Pv4UvGq/j12c7xXlx1CE2N4L2k8xes8n+X3sngj9kLVvH/OPX5wdv0Xkl9jdu/Xe89ndrI1ItfBuKr5DPJ+4cLG58tzWeHV+hc/9eQ1eyhaNjG2yuXhZSCNlIntTTTvNdNN5jpaMJdZ8cueYs+XynNPS88hWPinglW7uZZQdipIbI2vko+SvtaRn3vHMZ0mZeSeuzInBEnf88gq/O/lPXl8D3ev5TsmDSerTJ8HZMc0yPHP+zlUkJN03pvLE93mFr7R+//HEFjIoT5iVDc64PkMsSd+xVZ48F66TWEP8lEbq+x2ABTG3sJhUyEBsqUhqKface0rEUcnPZOW51LzIQBLJO4VLbgqV0LNmn5t7enquzZI/p6EWEiGllU5qRpkkq1YBP70qGJpUTw0i0qSLypDZSqtNWmu9OUfNXnrt0lvvXfvoU2EwFW3aVXXoHHkUKExGGz0MHWPMyaSToSd3T66Yc+VVVl2y2upL11jTgI9VE2vWTW3Y3HmXTfnvtnvYuseeJx2gdOqR004/esaZF6zdcuuV226/esedX1l7s/pj1tJPmfvrrKU3a56x+lzXv2eN071/GyI5nYjnjIzlmsh49wwA6Ow5i5pqzZ45z1kcmaKQTNaSeHJ28oyRwXpSlpu+cvc9c3+ZtyD1b+Ut/ylzwVP3/8hc8NS9mfs1b7/J2p6PopQnQV6FHtNYLsR2655ZZ14rXnhH/Q+XJ1aY1lrGiuJqnQXZGtr23LukdWQMy3kyNaI5TqCGKumNY/7PCP/gGP7pjTEvu9CJkQ8/hr3G3FZuvN0ISr9L2h6NYz9n7yPNyKGRMmWjHsRSlvZ9LqHcPtwqMtYKfA0vy7y6GA74aq+i+1iRSJbS3WanmMkeDMPwJVsrWA2Cv+aNHZxuXTM0YsklN85uCOrsOrrly4LHXJuMzgZCr5ws/eYtBdw6uBXZ7tKK7C19UP0W970oSSPThzT4fu8ECfOsbmB1PeNdAH4bgDby1eaKdfV+pG6Hy7Z2wj5pDf71CbyH3UlZenQIYl2gq8z4gGOqfUHD5B6mZ0qde92DtNywCLpZWQT2Nr2FdY3ZjIj0dPX0cpXoS0/DbywE854j23VrzGNn5FZGXhL0Mw2uww+nEvrGerdot8L+82l2TmuUidabV6vAE4kWs6nxRD21x6ozkNO89ADRY11WlVQxPCxM5oYWdqLYmJrlFpuJ0l4WxViC1bUoVkVlZaiGR83KMSbkrnkukKHqNiWWB6EYxDS1VUEHMMuUZKFE2XiTqs0KiR5McUJfF9BIr/eUQbWtenTEMwu1u8kj4GCf3RYxuWXZgGS8mMcdxI9bk/XJAkLDgl3dAoNhIQg3i9B1V+6tbWJyWh/XtnnYeyv4O027T1AN77DsVYhfqhc+ukk6Sb6xXQWQuwNHsJ3backzVXWnViGl0YAWnqdOTM4mLfY4u0GuNQZqh4kWFLnkFL/Rqw6MUxMA75C57lS1Whu6OzQ9s0Ncr8fMCDiWrPQevALbuWjTU8t+RaygJDaEI3tA7sl5WKtzT/ZwIjVWK+wLZxfn4Ch3Q7UL0FBqgy0iFNMWKYy6bRpOmrsybL8P9Iuj/WAcu/KbY5hmu3/2crVNG2REOnUNO0CPmj/FOthGTRmBqqnsVPGXAwpGc0Cxl0NI0Liy66NFfHPA3ZkK30Hp/Gmj1CHUK1QIfM/7shom8zIGs1xrYC/6OKXLAV999uEcTRg6jCQjJR37oL5F2f3NyBoBEWeH8KdZidtJw9KENBT5OGtExlx1gy3Iorr8tA2/odrNUqhqCMpSpEUvKMXrUAejrIyjZaDSdtrGBsZqxBH6im1cRHS3kTBWt6TNzlZIczQs4wBchbd0hwiYhYpZ0dq360ISy8lMduG/OdP2CNmCAXBtekBfj9kbvyp3NBZQJwXESBTPpfTLs3JJC3oCzdxKXSO1wPPmI5e/++q6J1p/HdlbJI3cWVAxOgeByQfVd2E7jAOSfpGQO9F+NjK1drh1rjvwwoj9UvGdSCDykdLuabdJ2T0aZZDMRKW3wtxP2VBqFCTth6Eb1Arb7rvk08XjBGu2oAvTUmE2GAgWB/vwH60KBn/0U5kOdQd16wwq4rgA6GQ/x7AOuRO7DtmJhY0f2sVLT4rdhTR1doKcjSNiJ9WVkPkaj1LMEz53gYUGIawx+BofgNiga6UNEolvubMexJa1aRJHr23SYndg0CJgQ5wTWQJy/cB1OKTSdz6T0c4uGK04NoacMaHT2d0zon1mSCA1NE7JTSvSDbIzBNCpCLqhXxUlzKdU4gSK25FSDhE4eT2fEoyX2pgDOmttooVIaQHINyv+MirzwcGR6glIF5AelWnO4lYSw4LxY/TP7dYDCKmIuQRUV1CO8GMKcBl6EazUWAI+oEU8JHGYTL+kpCEGiLBVx6txOGm5tTQ5fAHbN7yb6xPlRkQmHJVhup5xKgFsgSr0dCwv/ctKO84RcUWAMY468LrginRnKt6s+2MNWJvbF8rHje7RTuAgh5FYAf2oDIdXzJwC7QszJ77IAgGga4BAaGpJvBh+iakgz0SL0K6FWja7VuDgIcaioi6EC6bctLER+8oSYUgsOsR9GBASGOhwMqjJHBBMZTvkAkUs4qTrEZ9c7cpTKNxx73SHMNkoRm6ljXO1QR+xK54O3c1wyMGLrhT4HYS+AB5XaRcmnRg3883Hg/5dkLQmygOTkTvcA6YWRHszTXFBXObWL7qsqeXJgXIrOGx8Ln3MqLgmxqC6dlyvOsBd49zC7ahD3Wui/9kXt8Mo/kAn10ZPDnFjhsZ2K/dnJ+e+vKdOZ8EQXoiMeyR0sZtO1wo84K6JCMBItwIfqYg7o+NeqD0aluc5xKTPFdwTFIILis4o0ChFe7h1O88BfyindQ/fw0pYKkzX3lDmPhXLtmhS+oSYRqNjIUmkL2GEcFQtUEsQee+JAoeBoWAkcdBNc2FsmDGWSxqYPzIvhY1RQgbBLgaaiHJnLy0l7DGsiOhgbJBuxWvSgyPueM5Kg6ieYLj0ihcRkcG04+oE2zPZBxim5gRQBOxTKl3BJtvyQRss1FgzCF9khE4K+wX24CgAdSiQtEA3hV+8z2q0j0g+waZ5QI+u4tidKRNmaGdxHi0KMFordJ6jEEtE0XAlXEzD543kSAPNdoNGsFky4lNgVcU7Uip5tzRIKUbv2nGiN1gQdsMdZZf3ZKfCLywSpoBZ6VzpHsJV2OfBm6FrsERFgIiuS22h5yC8qU8UtHjtx07ecPaML4dmVmbBzCD4MUAgLAH1l+3CAr8L2k69UHv4JzXXXdVqubQIl0DBaXGPlUMRFKoGFjy5etYmwUZSGBcaX4LMlwQhY0IxBTTHhOIRQoAHmJRlobIU0+01g4ODbGUcG/wEZ8GnaEGjI8C5YGNx+OZPbkaCRqn5luQ8nICHNOLuDYgT4MaQVc93IMezchGGmUaBHsSBsxZe3zCraNesBHmS+eLtxOjUejqzPy0CPX1Mn2YhfPsQaQ/oQ2Q73XL1RPwXfImLcke5jzduAJY2kBbEEmaholHDtYMJlRhhpBCPCZsPfLn7wezFtuuE2ACQe0AxuABmYXcZCl1loqAUst1TW8Z8rkC8aDu9sblIAn6ou2XHUFMN9YBG/HMCHvBKWRgQNW9r55gG75MPGjP5NMckuH+oNcvjnv2ZFmFH/8Fa8W83NpkCENc9OlaKHL4kCtNADZ0StgTOVlo+dVpkteNG5gBp44yHwRsN4kU8AZ13Tsoq0WC0AIagInCdDXBDqLhaxz8CD0Fmz+2IfZY0N1HAQGCPDj0elSuUH9vx9hgGXLscli8IGrqu3BdSxGMs4ua7725j6cdWUqZGurGLF4XbmEo0N8eKQUQw0diEjuAzcI+4xASNDGj14y7SOkpvAwQuhoQ1p4YMoHfkDPmmAlqulDVFSGszACv+qy8Hk5YWCBQQfyxOhXYGHEct43DQZKXVBFEYJFBBYTFC9e4Dv21kl+oyoIHBj1MCxF1cejv5YL95u0dAkNumm2lAzjupiYq4WzVPO30PwKGLVI/TrsUowhxG9tuzK0/csrHPWI+U3WYZJMC8WECoD1GYa+MAsTc4XBwS7hgAY/wavag/iIJs43bbfRiQUiPBBBs22c8jAgxl5gfey3QK1B9NKo2YeWgPkOZTBK4BdD2C4J4mwQKIZnHfjc3F11Bi+AiVegDWoERHd3i4HYdxqmS31b5KPCRLb3riEnIBu/jTBzqQOdy8sw0mmiNz5gxF22iFvElAOakmIdPM4l4YE2HoxXFHsJ4e7hT0i0pBa9kTNqbRFBE7b/rc/rJphDCD68k1cAAdG42iBjf0DSdHr+VPC5QKpVdDUNx9UlTItRPdwY8aUCfvjEY/RFDhre17k3iKhJTdoZj/7wTd+PLq8Yde8ekmO1Yaj2OYvA3CD/yMIx1eA8Ufb1HxqAGLnjnQ6qs/cEypeIc58RN3PiMiV96J+6MA6HH48w1MLQqfOp1A8p7HKC8KuDBDKN6J3wfomCpEiQYNamMqCgtXiK3ELZ5D5Ht2P4kpzNhrjBA87K4Ao46ihMuGBBit5XWSsRbwPjYa9M3LUjZhSlQpHZV5/v2/WTLW5qi1STtZsBQTfxz84dTjaxr4bdiwtumNvX8hqw+9dX18TdnYVsYDQM7r2MF+sJn+rM8rDsdWsNSMph5eNA/Wkfs0604MH3rDSyZvn2d+nrzR/4Gnz2M9+jZUaDAQXQ8W58kI+cA8lmZKqPDYvujhIGaFcqK7esQMh4bWIClycbU4GiS1aACu6mGvRqOxFFwu7Fth7RMOX8ubOWygt0eIm9/k0tycetyuHSby514tDErXWRzXg52QMQaUCkPjf8wFSRWUG+JygMf1JrU9UoFJc+8Qd+ouGjg278r9SdyN4siG8lS9Zl0VaKRyF9pTkdbw0NQEFYRtJmsbO8tIWDIK8fpjaPoNmkuasvXQBt7Zn7G1GqkmsEIJflTqUej8Jcw/HcOfvvi7x789EAGicebG/wIbVgBEIY3a9AAAAAZiS0dEAP8A/wD/oL2nkwAAAAlwSFlzAAAOxAAADsQBlSsOGwAAAAd0SU1FB+QKGgw3InhoRNoAAAE7SURBVGje7ZntDYQgDIZPwxROwD7M6T5O4Bz3g6RpBPlqIRz3En9cNGfL07eljdt53R+ssrUDAWABFmABFmCx5ewBWD/My9nDX3PBcvYQdmfqW/IvPK/7vO5+vMwC2SEP3ugC3zsrZ8j6FU5Dn3oDaJo2zYeezTBjcq/4by3fjCSeU4krWu/plFTxedmm9MElBDdIWdnTJ0zVwpCmJRC1m7VCNzmvNontKgkYhss75K/CHrJ5D2SlRDWSTFRIw6iL3KeHyngDmdZCVd8QLVt9T8M2G7XhInzZHTb7Q3+MGlJTFsWcX1ztigW4pAUh68JGTF9Zb+9NlJ5moXFe4VhXW8KyOORTkRnTQD1KWEmtIUFlCdLNhOcq4jLy8qSem1VPq2YdIa9t1U9hPcadDd8NMe4AFmABFmD9/foC1zPyZVoU5vgAAAAASUVORK5CYII=",alt:"DinkedIn"})})})}),Object(r.jsxs)("div",{id:"authlinks",children:[Object(r.jsx)("div",{id:"filter",children:Object(r.jsx)("input",{onChange:x,placeholder:"Filter posts..."})}),c]})]})},g=(n(15),function(e){var t=e.id,n=e.username,s=e.current,u=Object(c.useState)([]),a=Object(o.a)(u,2),i=a[0],d=a[1],j=Object(c.useState)(!1),l=Object(o.a)(j,2),h=l[0],f=l[1];Object(c.useEffect)((function(){h?f(!1):O(t).then((function(e){console.log("pending reqs:",e),d(e)}))}),[h]);return console.log("username",n),console.log("current",s),n!==s||!i||i.length<1?null:Object(r.jsxs)("aside",{children:[Object(r.jsx)("h3",{children:"!You have pending friend requests!"}),Object(r.jsxs)("table",{children:[Object(r.jsx)("thead",{children:Object(r.jsxs)("tr",{children:[Object(r.jsx)("th",{children:"username"}),Object(r.jsx)("th",{children:"first name"}),Object(r.jsx)("th",{children:"last name"})]})}),Object(r.jsx)("tbody",{children:i.map((function(e,t){return Object(r.jsxs)("tr",{children:[Object(r.jsx)("td",{children:e.issuer.username}),Object(r.jsx)("td",{children:e.issuer.firstName}),Object(r.jsx)("td",{children:e.issuer.lastName}),Object(r.jsx)("td",{children:Object(r.jsx)("button",{onClick:function(){return t=e.issuer.username,void(confirm("Are you sure you want to accept the friendship of ".concat(t,"?"))&&(A(s,t).then((function(e){console.log(e)})),f(!0)));var t},type:"button",children:"Accept"})}),Object(r.jsx)("td",{children:Object(r.jsx)("button",{onClick:function(){return t=e.issuer.username,void(confirm("Are you sure you want to reject the friendship of ".concat(t,"?"))&&(p(s,t).then((function(e){console.log(e)})),f(!0)));var t},type:"button",children:"Reject"})})]},t)}))})]})]})}),v=function(e){e.userId,e.postFilter;return Object(c.useEffect)((function(){}),[]),Object(r.jsxs)("section",{children:[Object(r.jsx)("h3",{children:"Posts"}),Object(r.jsx)("ul",{children:Object(r.jsx)("li",{children:"asdf"})})]})},B=function(){var e=new URLSearchParams(window.location.search).get("id"),t=Object(c.useState)({}),n=Object(o.a)(t,2),s=n[0],u=n[1],i=Object(c.useState)(""),d=Object(o.a)(i,2),j=d[0],l=d[1];return Object(c.useEffect)((function(){f(e).then((function(e){return u(e)})),b().then((function(e){return l(e)}))}),[]),s.username?Object(r.jsxs)("div",{children:[Object(r.jsx)(m,{current:j.username,username:s.username}),Object(r.jsx)("h2",{children:"Wall of ".concat(s.username)}),Object(r.jsx)(g,{id:e,username:s.username,current:j.username}),Object(r.jsx)(v,{}),Object(r.jsx)(a,{})]}):null};u.a.render(Object(r.jsx)(B,{}),document.getElementById("root"))}},[[16,1,2]]]);
//# sourceMappingURL=main.52e798e3.chunk.js.map