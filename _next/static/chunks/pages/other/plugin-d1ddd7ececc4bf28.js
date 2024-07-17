(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[376],{9e3:function(e,n,i){(window.__NEXT_P=window.__NEXT_P||[]).push(["/other/plugin",function(){return i(9779)}])},9779:function(e,n,i){"use strict";i.r(n),i.d(n,{__toc:function(){return o}});var s=i(5893),t=i(2673),a=i(3393),l=i(2702);i(9128);var r=i(2643);let o=[{depth:2,value:"Concept",id:"concept"},{depth:2,value:"Warning and consideration",id:"warning-and-consideration"},{depth:2,value:"General",id:"general"},{depth:2,value:"Android",id:"android"},{depth:3,value:"1/ Create the plugin",id:"1-create-the-plugin"},{depth:3,value:"2/ register the plugin",id:"2-register-the-plugin"},{depth:2,value:"ios",id:"ios"},{depth:3,value:"1/ podspec integration",id:"1-podspec-integration"},{depth:3,value:"2/ Create the plugin",id:"2-create-the-plugin"},{depth:3,value:"3/ Register the plugin",id:"3-register-the-plugin"}];function _createMdxContent(e){let n=Object.assign({h1:"h1",p:"p",h2:"h2",pre:"pre",code:"code",span:"span",h3:"h3"},(0,r.a)(),e.components);return(0,s.jsxs)(s.Fragment,{children:[(0,s.jsx)(n.h1,{children:"Plugin (experimental)"}),"\n",(0,s.jsx)(n.p,{children:"Since Version 6.4.0, it is possible to create plugins for analytics management and maybe much more.\nA sample plugin is available in the repository in: example/react-native-video-plugin-sample. (important FIXME, put sample link)"}),"\n",(0,s.jsx)(n.h2,{id:"concept",children:"Concept"}),"\n",(0,s.jsx)(n.p,{children:"Most of the analytics system which tracks player information (bitrate, errors, ...) can be integrated directly with Exoplayer or AVPlayer handles."}),"\n",(0,s.jsx)(n.p,{children:"This plugin system allows none intrusive integration of analytics in the react-native-package. It shall be done in native language (kotlin/swift)."}),"\n",(0,s.jsx)(n.p,{children:"The idea behind this system is to be able to plug an analytics package to react native video without doing any code change (ideally)."}),"\n",(0,s.jsx)(n.p,{children:"Following documentation will show on how to create a new plugin for react native video"}),"\n",(0,s.jsx)(n.h2,{id:"warning-and-consideration",children:"Warning and consideration"}),"\n",(0,s.jsx)(n.p,{children:"This is an experiental API, it is subject to change. The api with player is very simple but should be flexible enough to implement analytics system. If you need some metadata, you should implement setter in the new package you are creating."}),"\n",(0,s.jsx)(n.p,{children:"As api is flexible, it makes possible to missuse the system. It is necessary to consider the player handle as read-only. If you modify player behavior, we cannot garanty the good behavior of react-native-video package."}),"\n",(0,s.jsx)(n.h2,{id:"general",children:"General"}),"\n",(0,s.jsx)(n.p,{children:"First you need to create a new react native package:"}),"\n",(0,s.jsx)(n.pre,{"data-language":"shell","data-theme":"default",children:(0,s.jsx)(n.code,{"data-language":"shell","data-theme":"default",children:(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"npx"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:" "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-string)"},children:"create-react-native-library@latest"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:" "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-string)"},children:"react-native-video-custom-analytics"})]})})}),"\n",(0,s.jsxs)(n.p,{children:["Both android and iOS implementation expose an interface ",(0,s.jsx)(n.code,{children:"RNVPlugin"}),".\nYour ",(0,s.jsx)(n.code,{children:"react-native-video-custom-analytics"})," shall implement this interface and register itself as a plugin for react native video."]}),"\n",(0,s.jsx)(n.h2,{id:"android",children:"Android"}),"\n",(0,s.jsx)(n.p,{children:"There is no special requierement for gradle file.\nYou need two mandatory action to be able to receive player handle"}),"\n",(0,s.jsx)(n.h3,{id:"1-create-the-plugin",children:"1/ Create the plugin"}),"\n",(0,s.jsxs)(n.p,{children:["First you should instanciate a class which extends ",(0,s.jsx)(n.code,{children:"RNVPlugin"}),"."]}),"\n",(0,s.jsxs)(n.p,{children:["The proposed integration implement ",(0,s.jsx)(n.code,{children:"RNVPlugin"})," directly inside the Module file (",(0,s.jsx)(n.code,{children:"VideoPluginSampleModule"}),")."]}),"\n",(0,s.jsxs)(n.p,{children:["The ",(0,s.jsx)(n.code,{children:"RNVPlugin"})," interface only defines 2 functions, see description here under."]}),"\n",(0,s.jsx)(n.pre,{"data-language":"kotlin","data-theme":"default",children:(0,s.jsxs)(n.code,{"data-language":"kotlin","data-theme":"default",children:[(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"/**"})]}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * Function called when a new player is created"})}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"@param"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:" id: a random string identifying the player"})]}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"@param"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:" player: the instantiated player reference"})]}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     */"})}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"fun"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:" "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"onInstanceCreated"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"(id: "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"String"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:", player: "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"Any"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:")"})]}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"/**"})]}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * Function called when a player should be destroyed"})}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * when this callback is called, the plugin shall free all"})}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * resources and release all reference to Player object"})}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"@param"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:" id: a random string identifying the player"})]}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"@param"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:" player: the player to release"})]}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     */"})}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"fun"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:" "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"onInstanceRemoved"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"(id: "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"String"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:", player: "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"Any"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:")"})]})]})}),"\n",(0,s.jsx)(n.h3,{id:"2-register-the-plugin",children:"2/ register the plugin"}),"\n",(0,s.jsx)(n.p,{children:"To register this allocated class in the main react native video package you should call following function:"}),"\n",(0,s.jsx)(n.pre,{"data-language":"kotlin","data-theme":"default",children:(0,s.jsx)(n.code,{"data-language":"kotlin","data-theme":"default",children:(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"ReactNativeVideoManager."}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"getInstance"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"()."}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"registerPlugin"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"(plugin)"})]})})}),"\n",(0,s.jsxs)(n.p,{children:["The proposed integration register the instanciated class in ",(0,s.jsx)(n.code,{children:"createNativeModules"})," entry point."]}),"\n",(0,s.jsx)(n.p,{children:"Your native module can now track Player updates directly from Player reference and report to backend."}),"\n",(0,s.jsx)(n.h2,{id:"ios",children:"ios"}),"\n",(0,s.jsx)(n.h3,{id:"1-podspec-integration",children:"1/ podspec integration"}),"\n",(0,s.jsx)(n.p,{children:"Your new module shall be able to access to react-native-video package, then we must declare it as a dependency of the new module you are creating."}),"\n",(0,s.jsx)(n.pre,{"data-language":"podfile","data-theme":"default",children:(0,s.jsx)(n.code,{"data-language":"podfile","data-theme":"default",children:(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:'  s.dependency "react-native-video"'})})})}),"\n",(0,s.jsx)(n.h3,{id:"2-create-the-plugin",children:"2/ Create the plugin"}),"\n",(0,s.jsxs)(n.p,{children:["First you should instanciate a class which extends ",(0,s.jsx)(n.code,{children:"RNVPlugin"}),"."]}),"\n",(0,s.jsxs)(n.p,{children:["The proposed integration implement ",(0,s.jsx)(n.code,{children:"RNVPlugin"})," directly inside the entry point of the module file (",(0,s.jsx)(n.code,{children:"VideoPluginSample"}),")."]}),"\n",(0,s.jsxs)(n.p,{children:["The ",(0,s.jsx)(n.code,{children:"RNVPlugin"})," interface only defines 2 functions, see description here under."]}),"\n",(0,s.jsx)(n.pre,{"data-language":"swift","data-theme":"default",children:(0,s.jsxs)(n.code,{"data-language":"swift","data-theme":"default",children:[(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"/**"})]}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * Function called when a new player is created"})}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * @param player: the instantiated player reference"})}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     */"})}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"func"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:" "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"onInstanceCreated"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"("}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"player"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:": "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-constant)"},children:"Any"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:")"})]}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"/**"})]}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * Function called when a player should be destroyed"})}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * when this callback is called, the plugin shall free all"})}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * resources and release all reference to Player object"})}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     * @param player: the player to release"})}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-comment)"},children:"     */"})}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"func"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:" "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"onInstanceRemoved"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"("}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"player"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:": "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-constant)"},children:"Any"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:")"})]})]})}),"\n",(0,s.jsx)(n.h3,{id:"3-register-the-plugin",children:"3/ Register the plugin"}),"\n",(0,s.jsx)(n.p,{children:"To register this allocated class in the main react native video package you should register it by calling this function:"}),"\n",(0,s.jsx)(n.pre,{"data-language":"swift","data-theme":"default",children:(0,s.jsx)(n.code,{"data-language":"swift","data-theme":"default",children:(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"ReactNativeVideoManager.shared."}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"registerPlugin"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-punctuation)"},children:"("}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"plugin"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-punctuation)"},children:":"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:" plugin"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-punctuation)"},children:")"})]})})}),"\n",(0,s.jsxs)(n.p,{children:["The proposed integration register the instanciated class in file ",(0,s.jsx)(n.code,{children:"VideoPluginSample"})," in the init function:"]}),"\n",(0,s.jsx)(n.pre,{"data-language":"swift","data-theme":"default",children:(0,s.jsxs)(n.code,{"data-language":"swift","data-theme":"default",children:[(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"import"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:" "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"react_native_video"})]}),"\n",(0,s.jsx)(n.span,{className:"line",children:" "}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"..."})}),"\n",(0,s.jsx)(n.span,{className:"line",children:" "}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"override"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:" "}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"init"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"() {"})]}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    super."}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-keyword)"},children:"init"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-punctuation)"},children:"()"})]}),"\n",(0,s.jsxs)(n.span,{className:"line",children:[(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"    ReactNativeVideoManager.shared."}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"registerPlugin"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-punctuation)"},children:"("}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:"plugin"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-punctuation)"},children:":"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-function)"},children:" self"}),(0,s.jsx)(n.span,{style:{color:"var(--shiki-token-punctuation)"},children:")"})]}),"\n",(0,s.jsx)(n.span,{className:"line",children:(0,s.jsx)(n.span,{style:{color:"var(--shiki-color-text)"},children:"}"})})]})}),"\n",(0,s.jsx)(n.p,{children:"Your native module can now track Player updates directly from Player reference and report to backend."})]})}let c={MDXContent:function(){let e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},{wrapper:n}=Object.assign({},(0,r.a)(),e.components);return n?(0,s.jsx)(n,{...e,children:(0,s.jsx)(_createMdxContent,{...e})}):_createMdxContent(e)},pageOpts:{filePath:"pages/other/plugin.md",route:"/other/plugin",timestamp:1721214861e3,pageMap:[{kind:"Meta",data:{index:"Introduction",installation:"Installation",component:"API",other:"Other",separator_versions:{type:"separator",title:""},updating:"Updating",changelog:{title:"Changelog",newWindow:!0,href:"https://github.com/TheWidlarzGroup/react-native-video/blob/master/CHANGELOG.md"},separator_community:{type:"separator",title:""},projects:"Useful projects"}},{kind:"Folder",name:"component",route:"/component",children:[{kind:"Meta",data:{props:"Properties",drm:"DRM",ads:"Ads",events:"Events",methods:"Methods"}},{kind:"MdxPage",name:"ads",route:"/component/ads"},{kind:"MdxPage",name:"drm",route:"/component/drm"},{kind:"MdxPage",name:"events",route:"/component/events"},{kind:"MdxPage",name:"methods",route:"/component/methods"},{kind:"MdxPage",name:"props",route:"/component/props"}]},{kind:"MdxPage",name:"index",route:"/"},{kind:"MdxPage",name:"installation",route:"/installation"},{kind:"Folder",name:"other",route:"/other",children:[{kind:"Meta",data:{caching:"Caching",misc:"Misc",debug:"Debugging","new-arch":"New Architecture",expo:"Expo",plugin:"Plugin (experimental)"}},{kind:"MdxPage",name:"caching",route:"/other/caching"},{kind:"MdxPage",name:"debug",route:"/other/debug"},{kind:"MdxPage",name:"expo",route:"/other/expo"},{kind:"MdxPage",name:"misc",route:"/other/misc"},{kind:"MdxPage",name:"new-arch",route:"/other/new-arch"},{kind:"MdxPage",name:"plugin",route:"/other/plugin"}]},{kind:"MdxPage",name:"projects",route:"/projects"},{kind:"MdxPage",name:"updating",route:"/updating"}],flexsearch:{codeblocks:!0},title:"Plugin (experimental)",headings:o},pageNextRoute:"/other/plugin",nextraLayout:a.ZP,themeConfig:l.Z};n.default=(0,t.j)(c)},2702:function(e,n,i){"use strict";var s=i(5893);i(7294),n.Z={head:(0,s.jsxs)(s.Fragment,{children:[(0,s.jsx)("meta",{name:"language",content:"en"}),(0,s.jsx)("meta",{name:"viewport",content:"width=device-width,initial-scale=1"}),(0,s.jsx)("meta",{name:"description",content:"Video component for React Native"}),(0,s.jsx)("meta",{name:"og:title",content:"React Native Video"}),(0,s.jsx)("meta",{name:"og:description",content:"A Video component for React Native"}),(0,s.jsx)("meta",{name:"og:image",content:"https://thewidlarzgroup.github.io/react-native-video/thumbnail.jpg"}),(0,s.jsx)("meta",{name:"twitter:card",content:"summary_large_image"}),(0,s.jsx)("meta",{name:"twitter:title",content:"React Native Video"}),(0,s.jsx)("meta",{name:"twitter:description",content:"A Video component for React Native"}),(0,s.jsx)("meta",{name:"twitter:image",content:"https://thewidlarzgroup.github.io/react-native-video/thumbnail.jpg"}),(0,s.jsx)("meta",{name:"twitter:image:alt",content:"React Native Video"}),(0,s.jsx)("link",{rel:"preconnect",href:"https://fonts.googleapis.com"}),(0,s.jsx)("link",{rel:"preconnect",href:"https://fonts.gstatic.com",crossOrigin:!0}),(0,s.jsx)("link",{href:"https://fonts.googleapis.com/css2?family=Orbitron:wght@400..900&display=swap",rel:"stylesheet"})]}),logo:(0,s.jsxs)("span",{children:["\uD83C\uDFAC ",(0,s.jsx)("strong",{children:"Video component"})," for React Native"]}),faviconGlyph:"\uD83C\uDFAC",project:{link:"https://github.com/TheWidlarzGroup/react-native-video"},docsRepositoryBase:"https://github.com/TheWidlarzGroup/react-native-video/tree/master/docs/",footer:{text:(0,s.jsxs)("span",{children:["Built with ❤️ by ",(0,s.jsx)("strong",{children:"React Native Community"})]})},useNextSeoProps:()=>({titleTemplate:"%s – Video"})}},5789:function(){}},function(e){e.O(0,[774,890,888,179],function(){return e(e.s=9e3)}),_N_E=e.O()}]);