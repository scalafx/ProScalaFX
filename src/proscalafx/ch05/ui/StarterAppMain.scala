package proscalafx.ch05.ui

import javafx.beans.{value => jfxbv}
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableColumn.CellDataFeatures
import javafx.{event => jfxe}
import javafx.{geometry => jfxg}
import javafx.{util => jfxu}
import proscalafx.ch05.model.{Person, StarterAppModel}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.KeyCombination
import scalafx.scene.layout.{HBox, StackPane, VBox, BorderPane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, Circle}
import scalafx.scene.web.{HTMLEditor, WebView}
import scalafx.scene.{Node, Scene}
import scalafx.stage.{Stage, Popup}


/**
 * @author Jarek Sacha
 */
object StarterAppMain extends JFXApp {

  private val model = new StarterAppModel()

  stage = new Stage {
    scene = new Scene(800, 600) {
      stylesheets = List(getClass.getResource("starterApp.css").toExternalForm)
      root = new BorderPane {
        top = new VBox {
          content = List(
            createMenus(),
            createToolBar()
          )
          center = createTabs()
        }
      }.delegate
    }
    title = "ScalaFX Starter App"
  }


  private def createMenus() = new MenuBar {
    menus = List(
      new Menu("File") {
        items = List(
          new MenuItem("New...") {
            graphic = new ImageView {
              image = new Image(this, "images/paper.png")
            }.delegate
            accelerator = KeyCombination.keyCombination("Ctrl +N")
            onAction = {e: ActionEvent => println(e.eventType + " occurred on MenuItem New")}
          },
          new MenuItem("Save")
        )
      },
      new Menu("Edit") {
        items = List(
          new MenuItem("Cut"),
          new MenuItem("Copy"),
          new MenuItem("Paste")
        )
      }
    )
  }


  private def createToolBar(): ToolBar = {
    val alignToggleGroup = new ToggleGroup()
    val toolBar = new ToolBar {
      content = List(
        new Button {
          id = "newButton"
          graphic = new ImageView {
            image = new Image(this, "images/paper.png")
          }
          tooltip = new Tooltip {
            text = "New Document... Ctrl+N"
          }
          onAction = println("New toolbar button clicked")
        },
        new Button {
          id = "editButton"
          graphic = new Circle {
            fill = Color.GREEN
            radius = 8
          }
        },
        new Button {
          id = "deleteButton"
          graphic = new Circle {
            fill = Color.BLUE
            radius = 8
          }
        },
        new Separator {
          orientation = jfxg.Orientation.VERTICAL
        },
        new ToggleButton {
          id = "boldButton"
          graphic = new Circle {
            fill = Color.MAROON
            radius = 8
          }
          onAction = {
            e: ActionEvent =>
              val tb = e.getTarget.asInstanceOf[javafx.scene.control.ToggleButton]
              print(e.eventType + " occurred on ToggleButton " + tb.id)
              print(", and selectedProperty is: ")
              println(tb.selectedProperty.value)
          }
        },
        new ToggleButton {
          id = "italicButton"
          graphic = new Circle {
            fill = Color.YELLOW
            radius = 8
          }
          onAction = {
            e: ActionEvent =>
              val tb = e.getTarget.asInstanceOf[javafx.scene.control.ToggleButton]
              print(e.eventType + " occurred on ToggleButton " + tb.id)
              print(", and selectedProperty is: ")
              println(tb.selectedProperty.value)
          }
        },
        new Separator {
          orientation = jfxg.Orientation.VERTICAL
        },
        new ToggleButton {
          id = "leftAlignButton"
          toggleGroup = alignToggleGroup
          graphic = new Circle {
            fill = Color.PURPLE
            radius = 8
          }
        },
        new ToggleButton {
          toggleGroup = alignToggleGroup
          id = "centerAlignButton"
          graphic = new Circle {
            fill = Color.ORANGE
            radius = 8
          }
        },
        new ToggleButton {
          toggleGroup = alignToggleGroup
          id = "rightAlignButton"
          graphic = new Circle {
            fill = Color.CYAN
            radius = 8
          }
        }
      )
    }

    alignToggleGroup.selectToggle(alignToggleGroup.toggles(0))
    alignToggleGroup.selectedToggle.onChange {
      val tb = alignToggleGroup.selectedToggle.get.asInstanceOf[javafx.scene.control.ToggleButton]
      println(tb.id() + " selecled")
    }

    toolBar
  }


  private def createTabs(): TabPane = {
    new TabPane {
      tabs = List(
        new Tab {
          text = "TableView"
          content = createTableDemoNode()
          closable = false
        },
        new Tab {
          text = "Accordion/TitledPane"
          content = createAccordionTitledDemoNode()
          closable = false
        },
        new Tab {
          text = "SplitPane/TreeView/ListView"
          content = createSplitTreeListDemoNode()
          closable = false
        },
        new Tab {
          text = "ScrollPane/Miscellaneous"
          content = createScrollMiscDemoNode()
          closable = false
        },
        new Tab {
          text = "HTMLEditor"
          content = createHtmlEditorDemoNode()
          closable = false
        },
        new Tab {
          // Name a reference to itself
          inner =>
          val webView = new WebView()
          text = "WebView"
          content = webView
          closable = false
          // NOTE: Using JavaFX Event in closure. Shouldn't implicit conversion take care of this?
          onSelectionChanged = {
            e: jfxe.Event => {
              val randomWebSite = model.randomWebSite()
              if (inner.selected()) {
                webView.engine.load(randomWebSite)
                println("WebView tab is selected, loading: " + randomWebSite)
              }
              println("")
            }
          }
        }
      )
    }
  }


  private def createTableDemoNode(): Node = {
    val firstNameColumn = new TableColumn[Person, String]("First Name") {
      // NOTE: Bug #10?  [[https://code.google.com/p/scalafx/issues/detail?id=10]]
      // Cell factory need to be assigned using JavaFX `setCellValueFactory`
      //      cellValueFactory = _.value.firstName
      prefWidth = 180
    }
    firstNameColumn.setCellValueFactory(new jfxu.Callback[CellDataFeatures[Person, String], jfxbv.ObservableValue[String]] {
      def call(param: CellDataFeatures[Person, String]) = param.getValue.firstName
    })

    val lastNameColumn = new TableColumn[Person, String]("First Name") {
      // NOTE: Bug #10?  [[https://code.google.com/p/scalafx/issues/detail?id=10]]
      // Cell factory need to be assigned using JavaFX `setCellValueFactory`
      //      cellValueFactory = _.value.firstName
      prefWidth = 180
    }
    lastNameColumn.setCellValueFactory(new jfxu.Callback[CellDataFeatures[Person, String], jfxbv.ObservableValue[String]] {
      def call(param: CellDataFeatures[Person, String]) = param.getValue.lastName
    })

    val phoneColumn = new TableColumn[Person, String]("First Name") {
      // NOTE: Bug #10?  [[https://code.google.com/p/scalafx/issues/detail?id=10]]
      // Cell factory need to be assigned using JavaFX `setCellValueFactory`
      //      cellValueFactory = _.value.firstName
      prefWidth = 180
    }
    phoneColumn.setCellValueFactory(new jfxu.Callback[CellDataFeatures[Person, String], jfxbv.ObservableValue[String]] {
      def call(param: CellDataFeatures[Person, String]) = param.getValue.phone
    })


    val table = new TableView[Person](model.getTeamMembers) {
      // NOTE: there may be an issue with assigning columns directly, do it through delegate
      //      columns ++= List(
      //        tc
      //      )
      delegate.getColumns.addAll(
        firstNameColumn.delegate,
        lastNameColumn.delegate,
        phoneColumn.delegate
      )
    }

    table.getSelectionModel.selectedItemProperty.onChange(
      (_, _, newValue) => println(newValue + " chosen in TableView")
    )

    table
  }


  private def createAccordionTitledDemoNode(): Node = new Accordion {
    panes = List(
      new TitledPane() {
        text = "TitledPane A"
        content = new TextArea {
          text = "TitledPane A content"
        }
      },
      new TitledPane {
        text = "TitledPane B"
        content = new TextArea {
          text = "TitledPane B content"
        }
      },
      new TitledPane {
        text = "TitledPane C"
        content = new TextArea {
          text = "TitledPane C' content"
        }
      }
    )

    expandedPane = panes.head
  }


  private def createSplitTreeListDemoNode(): Node = {
    val treeView = new TreeView[String] {
      minWidth = 150
      showRoot = false
      editable = false
      root = new TreeItem[String] {
        value = "Root"
        children = List(
          new TreeItem("Animal") {
            children = List(
              new TreeItem("Lion"),
              new TreeItem("Tiger"),
              new TreeItem("Bear")
            )
          },
          new TreeItem("Mineral") {
            children = List(
              new TreeItem("Coper"),
              new TreeItem("Diamond"),
              new TreeItem("Quartz")
            )
          },
          new TreeItem("Vegetable") {
            children = List(
              new TreeItem("Arugula"),
              new TreeItem("Broccoli"),
              new TreeItem("Cabbage")
            )
          }
        )

      }
    }

    val listView = new ListView[String] {
      items = model.listViewItems
    }

    treeView.selectionModel().setSelectionMode(SelectionMode.SINGLE)
    treeView.selectionModel().selectedItem.onChange(
      (_, _, newTreeItem) => {
        if (newTreeItem != null && newTreeItem.isLeaf) {
          model.listViewItems.clear()
          for (i <- 1 to 10000) {
            model.listViewItems += newTreeItem.getValue + " " + i
          }
        }
      }
    )

    new SplitPane {
      // NOTE: Using JavaFX way of adding items using `addAll`.
      //      items = List (
      //          treeView,
      //          listView
      //      )
      items.addAll(
        treeView,
        listView
      )
    }
  }


  def createScrollMiscDemoNode(): Node = {
    val radioToggleGroup = new ToggleGroup()
    val variousControls = new VBox {
      padding = new jfxg.Insets(10)
      spacing = 20
      content = List(
        new Button("Button") {
          onAction = {e: ActionEvent => println(e.eventType + " occured on Button")}
        },
        new CheckBox("CheckBox") {
          inner =>
          onAction = {
            e: ActionEvent =>
              println(e.eventType + " occured on CheckBox, and `selected` property is: " + inner.selected())
          }
        },
        new HBox {
          spacing = 10
          content = List(
            new RadioButton("RadioButton1") {
              toggleGroup = radioToggleGroup
            },
            new RadioButton("RadioButton2") {
              toggleGroup = radioToggleGroup
            }
          )
        },
        new Hyperlink("Hyperlink") {
          onAction = {e: ActionEvent => println(e.eventType + " occurred on Hyperlink")}
        },
        new ChoiceBox(model.choiceBoxItems) {
          selectionModel().selectFirst()
          selectionModel().selectedItem.onChange(
            (_, _, newValue) => println(newValue + " chosen in ChoiceBox")
          )
        },
        // NOTE: Using experimental implementation of MenuButton
        new sfxext.scene.control.MenuButton("MenuButton") {
          items = List(
            new MenuItem("MenuItem A") {
              onAction = {ae: ActionEvent => {println(ae.eventType + " occurred on Menu Item A")}}
            },
            new MenuItem("MenuItem B")
          )
        },
        // NOTE: Using experimental implementation of SplitMenuButton
        new sfxext.scene.control.SplitMenuButton {
          text = "SplitMenuButton"
          onAction = {ae: ActionEvent => {println(ae.eventType + " occurred on SplitMenuButton")}}
          items = List(
            new MenuItem("MenuItem A") {
              onAction = {ae: ActionEvent => {println(ae.eventType + " occurred on Menu Item A")}}
            },
            new MenuItem("MenuItem B")
          )
        },
        new TextField {
          promptText = "Enter user name"
          prefColumnCount = 16
          text.onChange {println("TextField text is: " + text())}
        },
        new PasswordField {
          promptText = "Enter password"
          prefColumnCount = 16
          text.onChange {println("PasswordField text is: " + text())}
        },
        new HBox {
          spacing = 10
          content = List(
            new Label {
              text = "TextArea"
            },
            new TextArea {
              prefColumnCount = 12
              prefRowCount = 4
              text.onChange {println("TextArea text is: " + text())}
            }
          )
        },
        new ProgressIndicator {
          prefWidth = 200
          progress <== model.rpm / model.maxRpm
        },
        new Slider {
          prefWidth = 200
          min = -1
          max = model.maxRpm
          value <==> model.rpm
        },
        new ProgressBar {
          prefWidth = 200
          progress <== model.kph / model.maxKph
        },
        new ScrollBar {
          prefWidth = 200
          min = -1
          max = model.maxKph
          value <==> model.kph
        }
      )
    }

    radioToggleGroup.selectToggle(radioToggleGroup.toggles(0))
    radioToggleGroup.selectedToggle.onChange {
      val rb = radioToggleGroup.selectedToggle.get.asInstanceOf[javafx.scene.control.ToggleButton]
      if (rb != null) println(rb.id() + " selecled")
    }

    val sampleContextMenu = new ContextMenu {
      // NOTE: Adding menu items through delegate. Adding directly does nothing.
      //      items ++= List(
      //        new MenuItem("MenuItemA") {
      //          onAction = {e: ActionEvent => println(e.eventType + " occurred on Menu Item A")}
      //        },
      //        new MenuItem("MenuItemB") {
      //          onAction = {e: ActionEvent => println(e.eventType + " occurred on Menu Item B")}
      //        }
      //      )
      delegate.getItems.addAll(
        new MenuItem("MenuItemA") {
          onAction = {e: ActionEvent => println(e.eventType + " occurred on Menu Item A")}
        }.delegate,
        new MenuItem("MenuItemB") {
          onAction = {e: ActionEvent => println(e.eventType + " occurred on Menu Item B")}
        }.delegate
      )
    }

    new ScrollPane {
      content = variousControls
      hbarPolicy = ScrollBarPolicy.ALWAYS
      vbarPolicy = ScrollBarPolicy.AS_NEEDED
      contextMenu = sampleContextMenu
    }
  }


  def createHtmlEditorDemoNode(): Node = {

    val htmlEditor = new HTMLEditor {
      htmlText = "<p>Replace this text</p>"
    }

    val viewHTMLButton = new Button("View HTML") {
      onAction = {
        e: ActionEvent => {
          val alertPopup = createAlertPopup(htmlEditor.htmlText)
          alertPopup.show(stage,
            (stage.width() - alertPopup.width()) / 2.0 + stage.x(),
            (stage.height() - alertPopup.height()) / 2.0 + stage.y())
        }
      }
      alignment = jfxg.Pos.CENTER
      // NOTE: No ScalaFX to create create `Insets`, need to use JavaFX constructor.
      //      margin = new Insets {
      //        top = 10
      //        right = 0
      //        bottom = 10
      //        left = 0
      //      }
      margin = new jfxg.Insets(10, 0, 10, 0)
    }

    new BorderPane {
      center = htmlEditor
      bottom = viewHTMLButton
    }
  }


  def createAlertPopup(popupText: String) = new Popup {
    inner =>
    content.add(new StackPane {
      content = List(new Rectangle {
        width = 300
        height = 200
        arcWidth = 20
        arcHeight = 20
        fill = Color.LIGHTBLUE
        stroke = Color.GRAY
        strokeWidth = 2
      },
        new BorderPane {
          center = new Label {
            text = popupText
            wrapText = true
            maxWidth = 280
            maxHeight = 140
          }
          bottom = new Button("OK") {
            onAction = {e: ActionEvent => inner.hide}
            alignment = jfxg.Pos.CENTER
            margin = new jfxg.Insets(10, 0, 10, 0)
          }
        }
      )
    }.delegate
    )
  }

}


