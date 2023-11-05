// Road.tsx

import React from "react";
import "./Road.css"; // This will be your CSS file for the road

interface RoadProps {
  width: number;
  bottom: number;
}

const Road: React.FC<RoadProps> = ({ width, bottom }) => {
  return <div className="road" style={{ width: width, bottom: bottom }}></div>;
};

export default Road;
