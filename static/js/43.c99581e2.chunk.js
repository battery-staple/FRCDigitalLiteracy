"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[43],{272:function(e,n,r){r.d(n,{Z:function(){return v}});var o=r(671),t=r(144),a=r(136),s=r(876),i=r(791),l=r(8),c=r(22),d=r(157),u=r(929),f=r.n(u),p=r(456),m=r(184),v=function(e){(0,a.Z)(r,e);var n=(0,s.Z)(r);function r(e){var t;return(0,o.Z)(this,r),(t=n.call(this,e)).state={profile:null},t}return(0,t.Z)(r,[{key:"render",value:function(){var e,n,r=this;return(0,m.jsx)(l.Z,{sticky:"top",className:"Navbar",children:(0,m.jsxs)(c.Z,{children:[(0,m.jsx)(l.Z.Brand,{href:"/",children:"Digital Literacy"}),(0,m.jsx)(l.Z.Toggle,{"aria-controls":"basic-navbar-nav"}),(0,m.jsx)(h,{signedIn:null!==this.state.profile,pfpURL:null!==(e=null===(n=this.state.profile)||void 0===n?void 0:n.pfpURL)&&void 0!==e?e:null,onSuccess:function(e){console.log(e),"tokenId"in e&&"profileObj"in e&&r.setState({profile:{token:e.tokenId,pfpURL:e.profileObj.imageUrl}})},onLoginFailure:function(e){console.log(e)},onLogoutSuccess:function(){r.setState({profile:null})}})]})})}},{key:"componentDidMount",value:function(){var e=this,n=localStorage.getItem("loginToken");null!==n&&new Promise((function(e,r){var o=(0,p.Z)(n);"picture"in o?(console.log(o),e({token:n,pfpURL:o.picture})):r()})).then((function(n){e.setState({profile:n})}))}},{key:"componentDidUpdate",value:function(e,n,r){var o,t;(null===(o=n.profile)||void 0===o?void 0:o.token)!==(null===(t=this.state.profile)||void 0===t?void 0:t.token)&&(null===this.state.profile?localStorage.removeItem("loginToken"):localStorage.setItem("loginToken",this.state.profile.token))}}]),r}(i.Component);function h(e){return e.signedIn?(console.log(e.pfpURL),(0,m.jsxs)("div",{children:[null!==e.pfpURL?(0,m.jsx)("img",{src:e.pfpURL,alt:"Profile",className:"pfp"}):null,(0,m.jsx)(u.GoogleLogout,{clientId:"1037281959356-1lomvo3e69p8j305qge2hfjrshk90vvh.apps.googleusercontent.com",onLogoutSuccess:e.onLogoutSuccess,onFailure:e.onLogoutFailure,render:function(e){return(0,m.jsx)(d.Z,{variant:"outline-primary",onClick:e.onClick,disabled:e.disabled,children:"Log Out"})}})]})):(0,m.jsx)(f(),{clientId:"1037281959356-1lomvo3e69p8j305qge2hfjrshk90vvh.apps.googleusercontent.com",onSuccess:e.onSuccess,onFailure:e.onLogoutFailure,render:function(e){return(0,m.jsx)(d.Z,{variant:"outline-primary",onClick:e.onClick,disabled:e.disabled,children:"Sign In"})},isSignedIn:!0})}},43:function(e,n,r){r.r(n),r.d(n,{default:function(){return M}});var o=r(671),t=r(144),a=r(136),s=r(876),i=r(791),l=r(413),c=r(987),d=r(694),u=r.n(d),f=r(162),p=r(543),m=r(472),v=r(184),h=["bsPrefix","className","variant","as"],g=i.forwardRef((function(e,n){var r=e.bsPrefix,o=e.className,t=e.variant,a=e.as,s=void 0===a?"img":a,i=(0,c.Z)(e,h),d=(0,f.vE)(r,"card-img");return(0,v.jsx)(s,(0,l.Z)({ref:n,className:u()(t?"".concat(d,"-").concat(t):d,o)},i))}));g.displayName="CardImg";var x=g,j=i.createContext(null);j.displayName="CardHeaderContext";var Z=j,b=["bsPrefix","className","as"],k=i.forwardRef((function(e,n){var r=e.bsPrefix,o=e.className,t=e.as,a=void 0===t?"div":t,s=(0,c.Z)(e,b),d=(0,f.vE)(r,"card-header"),p=(0,i.useMemo)((function(){return{cardHeaderBsPrefix:d}}),[d]);return(0,v.jsx)(Z.Provider,{value:p,children:(0,v.jsx)(a,(0,l.Z)((0,l.Z)({ref:n},s),{},{className:u()(o,d)}))})}));k.displayName="CardHeader";var y=k,N=["bsPrefix","className","bg","text","border","body","children","as"],C=(0,m.Z)("h5"),L=(0,m.Z)("h6"),S=(0,p.Z)("card-body"),I=(0,p.Z)("card-title",{Component:C}),P=(0,p.Z)("card-subtitle",{Component:L}),R=(0,p.Z)("card-link",{Component:"a"}),T=(0,p.Z)("card-text",{Component:"p"}),U=(0,p.Z)("card-footer"),w=(0,p.Z)("card-img-overlay"),F=i.forwardRef((function(e,n){var r=e.bsPrefix,o=e.className,t=e.bg,a=e.text,s=e.border,i=e.body,d=e.children,p=e.as,m=void 0===p?"div":p,h=(0,c.Z)(e,N),g=(0,f.vE)(r,"card");return(0,v.jsx)(m,(0,l.Z)((0,l.Z)({ref:n},h),{},{className:u()(o,g,t&&"bg-".concat(t),a&&"text-".concat(a),s&&"border-".concat(s)),children:i?(0,v.jsx)(S,{children:d}):d}))}));F.displayName="Card",F.defaultProps={body:!1};var H=Object.assign(F,{Img:x,Title:I,Subtitle:P,Body:S,Link:R,Text:T,Header:y,Footer:U,ImgOverlay:w}),O=r(22),B=r(743),D=r(677),E=r(157),q=r(272),M=function(e){(0,a.Z)(r,e);var n=(0,s.Z)(r);function r(e){var t;return(0,o.Z)(this,r),(t=n.call(this,e)).state={profile:null},t}return(0,t.Z)(r,[{key:"render",value:function(){return(0,v.jsxs)("div",{className:"TeacherHomepage",children:[(0,v.jsx)(q.Z,{}),(0,v.jsxs)(O.Z,{children:[(0,v.jsx)("h1",{children:"Unplayed"}),(0,v.jsx)(G,{cards:this.props.cards.filter((function(e){return e.progress<=.999}))}),(0,v.jsx)("h1",{children:"Completed"}),(0,v.jsx)(G,{cards:this.props.cards.filter((function(e){return e.progress>.999}))})]})]})}}]),r}(i.Component);function G(e){return(0,v.jsx)(B.Z,{className:"gy-3",children:e.cards.map(z).map((function(e){return(0,v.jsx)(D.Z,{xs:12,md:6,lg:4,xl:3,children:e})}))})}function z(e){return(0,v.jsxs)(H,{className:"LessonCard h-100",children:[(0,v.jsxs)(H.Body,{className:"d-flex flex-column",children:[(0,v.jsx)(H.Title,{children:e.name}),(0,v.jsx)(H.Text,{children:e.description}),(0,v.jsx)(E.Z,{variant:"primary",href:e.url,className:"mt-auto align-self-start",children:e.progress>.005?"Continue":"Start"})]}),(0,v.jsx)("div",{className:"TeacherHomepage-card-progress",style:{width:"".concat(100*e.progress,"%")}})]})}}}]);
//# sourceMappingURL=43.c99581e2.chunk.js.map