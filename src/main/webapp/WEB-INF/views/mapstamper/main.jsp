<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/main.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>MAPSTAMPER</title>
</head>


<body>

    <div id="app">

        <!-- HEADER -->
        <header>
            <div class="title">
                <h3>MAP STAMPER</h3>
            </div>
            <div class="controlBox">
                <input type="file" name="fileLoader" id="fileInput" style="display: none;">
                <span id="saveToImage" class="material-icons active">save</span>
                <span id="load" class="material-icons active">file_download</span>
                <span id="save" class="material-icons active">file_upload</span>
            </div>       
        </header>

        <!-- SIDE BAR --> 
        <div id="sidebar" class="toolbox">
            
            <h4>Stamp</h4>
            <section class="stamp">
                <div class="currentSymbol">
                    <div class="currentImageBox">
                        <img id="currentSymbol" src="http://10.10.0.20:9999/img/love_love_01.png" alt="symbol" style="width: 120px; height: auto;" data-aid="asset_love_love_01">
                    </div>
                    
                </div>
    
                <div class="randomStamp check">
                    <input type="checkbox" name="isRandomStamp" id="isRandomStamp">	
                    <h5>Random Stamp</h5>                
                </div>
    
                <div class="stampSize">
                    <h5>Size (<span id="showStampSize">25</span>)</h5>
                    <input type="range" min="1" max="100" value="25" step="1" id="controlStampSize">
                </div>
            </section>

            <section class="texture">
                <h4>Texture</h4>
                <div class="backMode check">            
                    <input type="checkbox" name="isBack" id="isBackMode">
                    <h5>Texture Mode</h5>
                </div>

                <div class="canvasPattern">
                    <div class="wrap">
                        <div class="foregroundPattern">
                            <h5>Foreground</h5>                    
                            <img id="foreImage" src="http://10.10.0.20:9999/img/texture_Land_02.png" data-aid="asset_texture_Land_02" alt="background" crossorigin="a">
                        </div>
            
                        <div class="backgroundPattern">
                            <h5>Background</h5>
                            <img id="backImage" src="http://10.10.0.20:9999/img/texture_Icesnow_04.png" data-aid="asset_texture_Icesnow_04" alt="foreground" crossorigin="anonymous">
                        </div>
                    </div>
                </div>

                <div class="vertex">
                    <h5>Vertices (<span id="showVertices">5</span>)</h5>
                    <input type="range" min="3" max="12" value="5" step="1" id="controlVertices">
                </div>
            </section>

            <section class="tool">
                <h4>Tools</h4>
                <div class="fuzziness check" style="display: none;">            
                    <input type="checkbox" name="isFuzz" id="isFuzziness">	
                    <h5>Fuzziness</h5>                         		
                </div>
    
                <div class="dragMode check">
                    <input type="checkbox" name="isDrag" id="isDragMode">
                    <h5>Drag Mode</h5>                	
                </div>            
    
                <div class="dragDistance">
                    <h5>Drag Distance (<span id="showDragDistance">25</span>)</h5>
                    <input type="range" min="25" max="200" value="25" step="1" id="controlDragDistance">
                </div>
            </section>

            <section class="canvasInfo">
                <h4>Canvas</h4>
                <div class="canvasSize">
                    <div class="input-group">
                        <h5>X (<span id="showCanvasXSize">1200</span>) </h5>
                        <input type="range" min=300 max="2000" id="sizeX" placeholder="sizeX" value="1200" class="size">
                    </div>
                    <div class="input-group">
                        <h5 class="input-group-addon">Y (<span id="showCanvasYSize">800</span>)</h5>
                        <input type="range" min=300 max="2000" id="sizeY" placeholder="sizeY" value="800" class="size">
                    </div>                    
                </div>
            </section>

        </div>    

        <!-- MAIN -->
        <main>                            
            <div id="canvasContainer">
                <!-- canvas here -->
            </div>
        </main>

        <!-- HISTORY -->
        <div id="history" class="toolbox">
            <div class="wrap">
                <div class="titleBox">
                    <div class="title">
                        <h5>History</h5>
                        <span id="symbolHistory" class="historyTabButton active" data-btn="1" >Symbol</span>
                        <span id="textureHistory" class="historyTabButton" data-btn="2" >Texture</span>
                        <span id="historyCloseButton" class="material-icons">close</span>
                    </div>
                    <div class="toolsButtons">
                        <span id="undoButton" class="material-icons">undo</span>
                        <span id="redoButton" class="material-icons">redo</span>
                    </div>    
                </div>
    
                <ul id="historyBoard" data-tab="1" class="historyBoard">
                    <!-- history here -->
                </ul>
                <ul id="textureHistoryBoard" class="historyBoard" style="display: none;" data-tab="2">

                </ul>
            </div>

        <div id="historyNavButton">
            <span id="historyNav">SHOW HISTORY</span>
        </div>
            

            <button id="clearHistory" style="display: none;">CLEAR</button>
        </div>

        <!-- ASSET -->
        <div id="asset" style="display: none;">
            <div class="wrap">
                <div class="titleBox">
                    <span class="title">ASSET</span>
                    <span id="assetCloseButton" class="material-icons">close</span>
                </div>
                <div id="assetBoard">
                    <!-- asset here -->              
                </div>        
            </div>    
        </div>

        <!-- ALRET DIALOG -->
        <div id="alretDialog">

        </div>

        <!-- TEMPLATES -->
        <template id="temp_asset">
            <li>
                <img>
            </li>
        </template>

        <template id="temp_asset_group">
            <h5 class="assetGroupTitle"></h5>
            <ul class="assetGroupList">

            </ul>
        </template>

        <template id="temp_asset_cat">
            <div class="assetCatContainer">
                <h3 class="assetCatTitle"></h3>
            </div>
        </template>

        <template id="temp_history">
            <li class="stack">                    
                <span>
                    <img class="img" src="images/wallpaper_black.svg">
                </span>
                <span class="desc">TEXTURE ADDED</span>
            </li>
        </template>        
    </div>

    
  </body>

<script src="js/drawing.js"></script>
</html>